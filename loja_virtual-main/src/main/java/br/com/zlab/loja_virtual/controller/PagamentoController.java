package br.com.zlab.loja_virtual.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.zlab.loja_virtual.dto.BoletoGeradoApiJuno;
import br.com.zlab.loja_virtual.dto.CobrancaJunoAPI;
import br.com.zlab.loja_virtual.dto.ConteudoBoletoJuno;
import br.com.zlab.loja_virtual.dto.ErroResponseApiJuno;
import br.com.zlab.loja_virtual.dto.PagamentoCartaoCredito;
import br.com.zlab.loja_virtual.dto.PaymentsCartaoCredito;
import br.com.zlab.loja_virtual.dto.RetornoPagamentoCartaoJuno;
import br.com.zlab.loja_virtual.dto.VendaCompraLojaVirtualDTO;
import br.com.zlab.loja_virtual.model.AccessTokenJunoAPI;
import br.com.zlab.loja_virtual.model.BoletoJuno;
import br.com.zlab.loja_virtual.model.VendaCompraLojaVirtual;
import br.com.zlab.loja_virtual.repository.BoletoJunoRepository;
import br.com.zlab.loja_virtual.repository.Vd_Cp_Loja_virt_repository;
import br.com.zlab.loja_virtual.service.HostIgnoringCliente;
import br.com.zlab.loja_virtual.service.ServiceJunoBoleto;
import br.com.zlab.loja_virtual.service.VendaService;
import br.com.zlab.loja_virtual.util.ApiTokenIntegracao;
import br.com.zlab.loja_virtual.util.ValidaCPF;

@Controller
public class PagamentoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Vd_Cp_Loja_virt_repository vd_Cp_Loja_virt_repository;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private ServiceJunoBoleto serviceJunoBoleto;
	
	@Autowired
	private BoletoJunoRepository boletoJunoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/pagamento/{idVendaCompra}")
	public ModelAndView pagamento(@PathVariable(value = "idVendaCompra",
								 required = false) String idVendaCompra) {
		
		ModelAndView modelAndView = new ModelAndView("payment");
		
		VendaCompraLojaVirtual compraLojaVirtual = vd_Cp_Loja_virt_repository.findByIdExclusao(Long.parseLong(idVendaCompra));
		
		if (compraLojaVirtual == null) {
			modelAndView.addObject("venda", new VendaCompraLojaVirtualDTO());
		}else {
			modelAndView.addObject("venda", vendaService.consultaVenda(compraLojaVirtual));
		}
		
		return  modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/finalizarCompraCartao")
	public ResponseEntity<String> finalizarCompraCartao(
			@RequestParam("cardHash") String cardHash,
			@RequestParam("cardNumber") String cardNumber,
			@RequestParam("holderName") String holderName,
			@RequestParam("securityCode") String securityCode,
			@RequestParam("expirationMonth") String expirationMonth,
			@RequestParam("expirationYear") String expirationYear,
			@RequestParam("idVendaCampo") Long idVendaCampo,
			@RequestParam("cpf") String cpf,
			@RequestParam("qtdparcela") Integer qtdparcela,
			@RequestParam("cep") String cep,
			@RequestParam("rua") String rua,
			@RequestParam("numero") String numero,
			@RequestParam("cidade") String cidade, 
			@RequestParam("estado") String estado) throws Exception{
		
		VendaCompraLojaVirtual vendaCompraLojaVirtual = vd_Cp_Loja_virt_repository.
				findById(idVendaCampo).orElse(null);
		
		if(vendaCompraLojaVirtual == null) {
			return new ResponseEntity<String>("Código da venda não existe",HttpStatus.OK);
		}
		
		String cpfLimpo = cpf.replace("\\", "").replaceAll("\\-", "");
		
		if(!ValidaCPF.isCPF(cpfLimpo)) {
			return new ResponseEntity<String>("CPF informado é inválido",HttpStatus.OK);
		}
		
		if(qtdparcela > 12 || qtdparcela <= 0) {
			return new ResponseEntity<String>("A quantidade de parcelas deve ser dde 1 até 12.",HttpStatus.OK);
		}
		
		if (vendaCompraLojaVirtual.getValorTotal().doubleValue() <= 0) {
			return new ResponseEntity<String>("O valor da venda não pode ser zero(0).",HttpStatus.OK);
		}
		
		AccessTokenJunoAPI accessTokenJunoAPI = serviceJunoBoleto.obterTokenApiJuno();
		
		if (accessTokenJunoAPI == null) {
			return new ResponseEntity<String>("Autorização bancaria não foi encontrada",HttpStatus.OK);
		}
		
		CobrancaJunoAPI cobrancaJunoAPI = new CobrancaJunoAPI();
		cobrancaJunoAPI.getCharge().setPixKey(ApiTokenIntegracao.CHAVE_BOLETO_PIX);
		cobrancaJunoAPI.getCharge().setDescription("Pagamento da venda: " + vendaCompraLojaVirtual.getId() + "Para o cliente: " + vendaCompraLojaVirtual.getPessoa().getNome());
		
		if(qtdparcela == 1) {
			cobrancaJunoAPI.getCharge().setAmount(vendaCompraLojaVirtual.getValorTotal().floatValue());
		}else {
			BigDecimal valorParcela = vendaCompraLojaVirtual.getValorTotal().divide(BigDecimal.valueOf(qtdparcela),RoundingMode.DOWN).setScale(2, RoundingMode.DOWN);
			cobrancaJunoAPI.getCharge().setAmount(valorParcela.floatValue());
		}
		
		cobrancaJunoAPI.getCharge().setInstallments(qtdparcela);
		
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
		cobrancaJunoAPI.getCharge().setDueDate(dateFormat.format(dataVencimento.getTime()));
		
		cobrancaJunoAPI.getCharge().setFine(BigDecimal.valueOf(1.00));
		cobrancaJunoAPI.getCharge().setInterest(BigDecimal.valueOf(1.00));
		cobrancaJunoAPI.getCharge().setMaxOverdueDays(7);
		cobrancaJunoAPI.getCharge().getPaymentTypes().add("CREDIT_CARD");
		
		cobrancaJunoAPI.getBilling().setName(holderName);
		cobrancaJunoAPI.getBilling().setDocument(cpfLimpo);
		cobrancaJunoAPI.getBilling().setEmail(vendaCompraLojaVirtual.getPessoa().getEmail());
		cobrancaJunoAPI.getBilling().setPhone(vendaCompraLojaVirtual.getPessoa().getTelefone());
		
		Client client = new HostIgnoringCliente("https://api.juno.com.br/").hostIgnoringCliente();
		WebResource webResource = client.resource("https://api.juno.com.br/charges");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(cobrancaJunoAPI);
		
		ClientResponse clientResponse = webResource
				.accept("application/json;charset=UTF-8")
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("X-API-Version", 2)
				.header("X-Resource-Token", ApiTokenIntegracao.TOKEN_PRIVATE_JUNO)
				.header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
				.post(ClientResponse.class, json);
		
		String stringRetorno = clientResponse.getEntity(String.class);
		
		if (clientResponse.getStatus() != 200) {
			ErroResponseApiJuno jsonRetornoErro = objectMapper.
					readValue(stringRetorno, new TypeReference<ErroResponseApiJuno>() { });
			
			return new ResponseEntity<String>(jsonRetornoErro.listaErro(),HttpStatus.OK);
		}
		
		clientResponse.close();
		
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		
		BoletoGeradoApiJuno jsonRetorno = objectMapper.
				readValue(stringRetorno, new TypeReference<BoletoGeradoApiJuno>() { });
		
		int recorrencia = 1;
		
		List<BoletoJuno> boletosJuno = new ArrayList<BoletoJuno>();
		
		for (ConteudoBoletoJuno c: jsonRetorno.get_embedded().getCharges()) {
			
			BoletoJuno boletoJuno = new BoletoJuno();
			
			boletoJuno.setChargeIdCartao(c.getId());
			boletoJuno.setCheckoutUrl(c.getCheckoutUrl());
			boletoJuno.setDataVencimento(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyy-MM-dd").parse(c.getDueDate())));
			boletoJuno.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
			boletoJuno.setIdChrBoleto(c.getId());
			boletoJuno.setIdPix(c.getPix().getId());
			boletoJuno.setImageInBase64(c.getPix().getImageInBase64());
			boletoJuno.setInstallmentLink(c.getInstallmentLink());
			boletoJuno.setLink(c.getLink());
			boletoJuno.setPayloadInBase64(c.getPix().getPayloadInBase64());
			boletoJuno.setQuitado(false);
			boletoJuno.setRecorrencia(recorrencia);
			boletoJuno.setValor(new BigDecimal(c.getAmount()).setScale(2, RoundingMode.HALF_UP));
			boletoJuno.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
			
			boletoJuno = boletoJunoRepository.saveAndFlush(boletoJuno);
			
			
			boletosJuno.add(boletoJuno);
			recorrencia ++;
		}
		
		if(boletosJuno == null || (boletosJuno != null && boletosJuno.isEmpty())) {
			return new ResponseEntity<String>("Não foi possivel gerar registro financeiro para o pagamento",HttpStatus.OK);
		}
		
		/**------------------------------------REALIZANDO PAGAMENTO POR CARTÃO DE CREDITO---------------------------------------------**/
		
		BoletoJuno boletoJunoQuitacao = boletosJuno.get(0);
		
		PagamentoCartaoCredito pagamentoCartaoCredito = new PagamentoCartaoCredito();
		pagamentoCartaoCredito.setChargeId(boletoJunoQuitacao.getChargeIdCartao());
		pagamentoCartaoCredito.getCreditCardDetails().setCreditCardHash(cardHash);
		pagamentoCartaoCredito.getBilling().setEmail(vendaCompraLojaVirtual.getPessoa().getEmail());
		pagamentoCartaoCredito.getBilling().getAddress().setState(estado);
		pagamentoCartaoCredito.getBilling().getAddress().setCity(cidade);
		pagamentoCartaoCredito.getBilling().getAddress().setStreet(rua);
		pagamentoCartaoCredito.getBilling().getAddress().setPostCode(cep.replaceAll("\\-", "").replaceAll("\\.", ""));
		
		
		Client clientCartao = new HostIgnoringCliente("https://api.juno.com.br/").hostIgnoringCliente();
		WebResource webResourceCartao = clientCartao.resource("https://api.juno.com.br/payments");
		
		ObjectMapper objectMapperCartao = new ObjectMapper();
		String jsonCartao = objectMapperCartao.writeValueAsString(pagamentoCartaoCredito);
		
		System.out.println("-------------Envio dados pagamento cartão-----------------------------------" + jsonCartao);
		
		ClientResponse clientResponseCartao = webResourceCartao
				.accept("application/json;charset=UTF-8")
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("X-API-Version", 2)
				.header("X-Resource-Token", ApiTokenIntegracao.TOKEN_PRIVATE_JUNO)
				.header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
				.post(ClientResponse.class, jsonCartao);
		
		String stringRetornoCartao = clientResponseCartao.getEntity(String.class);
		
		System.out.println("-------------Retorno dados pagamento cartão-----------------------------------" + stringRetornoCartao);
		
		if(clientResponseCartao.getStatus() != 200) {
			
			ErroResponseApiJuno erroResponseApiJuno = objectMapper.readValue(stringRetornoCartao, new TypeReference<ErroResponseApiJuno>() { });
			
			for (BoletoJuno boletoJuno : boletosJuno) {
				serviceJunoBoleto.cancelarBoleto(boletoJuno.getCode());
			}
			
			return new ResponseEntity<String>(erroResponseApiJuno.listaErro(),HttpStatus.OK);
			
		}
		
		RetornoPagamentoCartaoJuno retornoPagamentoCartaoJuno = objectMapperCartao.
				readValue(stringRetornoCartao, new TypeReference<RetornoPagamentoCartaoJuno>() { });
		
		if(retornoPagamentoCartaoJuno.getPayments().size() <= 0) {
			
			for (BoletoJuno boletoJuno : boletosJuno) {
				serviceJunoBoleto.cancelarBoleto(boletoJuno.getCode());
			}
			
			return new ResponseEntity<String>("Nenhum pagamento foi retornado para processar.",HttpStatus.OK);
		}
		
		PaymentsCartaoCredito cartaoCredito = retornoPagamentoCartaoJuno.getPayments().get(0);
		
		if (!cartaoCredito.getStatus().equalsIgnoreCase("CONFIRMED")) {
			for (BoletoJuno boletoJuno : boletosJuno) {
				serviceJunoBoleto.cancelarBoleto(boletoJuno.getCode());
			}
		}
		
		if(cartaoCredito.getStatus().equalsIgnoreCase("DECLINED")) {
			return new ResponseEntity<String>("Pagamento rejeitado",HttpStatus.OK);
		}else if (cartaoCredito.getStatus().equalsIgnoreCase("FAILED")) {
			return new ResponseEntity<String>("Pagamento falhou",HttpStatus.OK);
		}else if (cartaoCredito.getStatus().equalsIgnoreCase("NOT AUTHORIZED")) {
			return new ResponseEntity<String>("Pagamento não autorizado pela instituição responsável pelo cartão de crédito no caso a emissora do seu cartão",HttpStatus.OK);
		}else if (cartaoCredito.getStatus().equalsIgnoreCase("CUSTOMER_PAID_BACK")) {
			return new ResponseEntity<String>("Pagamento estornado a pedido do cliente",HttpStatus.OK);
		}else if (cartaoCredito.getStatus().equalsIgnoreCase("BANK_PAID_BACK")) {
			return new ResponseEntity<String>("Pagamento estornado a pedido do banco",HttpStatus.OK);
		}else if (cartaoCredito.getStatus().equalsIgnoreCase("PARTIALLY_REFUNDED")) {
			return new ResponseEntity<String>("Pagamento parcialmente estornado ",HttpStatus.OK);
		}else if (cartaoCredito.getStatus().equalsIgnoreCase("CONFIRMED")) {
			
			for (BoletoJuno boletoJuno : boletosJuno) {
				boletoJunoRepository.quitarBoletoById(boletoJuno.getId());
			}
			vd_Cp_Loja_virt_repository.updatefinalizaVenda(vendaCompraLojaVirtual.getId());
			
			/*iniciar processo de entrega aqui*/
			
			return new ResponseEntity<String>("sucesso",HttpStatus.OK);
		}
		
		
		
		
		return new ResponseEntity<String>("Nenhuma operação realizada",HttpStatus.OK);
	}	

}
