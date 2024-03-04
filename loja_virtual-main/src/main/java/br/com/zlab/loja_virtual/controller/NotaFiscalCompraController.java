package br.com.zlab.loja_virtual.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zlab.loja_virtual.dto.ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import br.com.zlab.loja_virtual.dto.ObejtoRequisicaoRelatorioProdutoAlertaEstoque;
import br.com.zlab.loja_virtual.dto.ObjetoRelatorioStatusCompra;
import br.com.zlab.loja_virtual.exception.handler.LojaVirtualException;
import br.com.zlab.loja_virtual.model.NotaFiscalCompra;
import br.com.zlab.loja_virtual.model.NotaFiscalVenda;
import br.com.zlab.loja_virtual.repository.NotaFiscalCompraRepository;
import br.com.zlab.loja_virtual.repository.NotaFiscalVendaRepository;
import br.com.zlab.loja_virtual.service.NotaFiscalCompraService;
import jakarta.validation.Valid;

@RestController
public class NotaFiscalCompraController {
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@Autowired
	private NotaFiscalVendaRepository notaFiscalVendaRepository;
	
	@Autowired
	private NotaFiscalCompraService notaFiscalCompraService;
	
	
	@ResponseBody
	@PostMapping(value = "/relatorioStatusCompra")
	public ResponseEntity<List<ObjetoRelatorioStatusCompra>> relatorioStatusCompra (@Valid 
			             @RequestBody  ObjetoRelatorioStatusCompra objetoRelatorioStatusCompra){
		
		List<ObjetoRelatorioStatusCompra> retorno = new ArrayList<ObjetoRelatorioStatusCompra>();
		
		retorno = notaFiscalCompraService.relatorioStatusVendaLojaVirtual(objetoRelatorioStatusCompra);
		
		return new ResponseEntity<List<ObjetoRelatorioStatusCompra>>(retorno, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping(value = "/relatorioProdCompradoNotaFiscal")
	public ResponseEntity<List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO>> relatorioProdCompradoNotaFiscal
	    (@Valid @RequestBody ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO obejtoRequisicaoRelatorioProdCompraNotaFiscalDto){
		
		List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO> retorno = 
				new ArrayList<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO>();
		
		retorno = notaFiscalCompraService.gerarRelatorioProdCompraNota(obejtoRequisicaoRelatorioProdCompraNotaFiscalDto);
		
		
		return new ResponseEntity<List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO>>(retorno, HttpStatus.OK);
		
	}
	
	
	
	@ResponseBody
	@PostMapping(value = "/relatorioProdAlertaEstoque")
	public ResponseEntity<List<ObejtoRequisicaoRelatorioProdutoAlertaEstoque>> relatorioProdAlertaEstoque
	    (@Valid @RequestBody ObejtoRequisicaoRelatorioProdutoAlertaEstoque obejtoRequisicaoRelatorioProdCompraNotaFiscalDto ){
		
		List<ObejtoRequisicaoRelatorioProdutoAlertaEstoque> retorno = 
				new ArrayList<ObejtoRequisicaoRelatorioProdutoAlertaEstoque>();
		
		retorno = notaFiscalCompraService.gerarRelatorioAlertaEstoque(obejtoRequisicaoRelatorioProdCompraNotaFiscalDto);
		
		
		return new ResponseEntity<List<ObejtoRequisicaoRelatorioProdutoAlertaEstoque>>(retorno, HttpStatus.OK);
		
	}
	
	
	
	
	@ResponseBody 
	@PostMapping(value = "/salvarNotaFiscalCompra")
	public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra notaFiscalCompra) throws LojaVirtualException { /*Recebe o JSON e converte pra Objeto*/
		
		if (notaFiscalCompra.getId() == null) {
		  
			if (notaFiscalCompra.getDescricaoObs() != null) {
				boolean existe = notaFiscalCompraRepository.existeNotaComDescricao(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());
			   
				if(existe) {
				   throw new LojaVirtualException("Já existe Nota de compra com essa mesma descrição : " + notaFiscalCompra.getDescricaoObs());
			   }
			}
			
			
		}
		
		if (notaFiscalCompra.getPessoa() == null || notaFiscalCompra.getPessoa().getId() <= 0) {
			throw new LojaVirtualException("A Pessoa Juridica da nota fiscal deve ser informada.");
		}
		
		
		if (notaFiscalCompra.getEmpresa() == null || notaFiscalCompra.getEmpresa().getId() <= 0) {
			throw new LojaVirtualException("A empresa responsável deve ser infromada.");
		}
		
		
		if (notaFiscalCompra.getContaPagar() == null || notaFiscalCompra.getContaPagar().getId() <= 0) {
			throw new LojaVirtualException("A cponta a pagar da nota deve ser informada.");
		}
		
		
		NotaFiscalCompra notaFiscalCompraSalva = notaFiscalCompraRepository.save(notaFiscalCompra);
		
		return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraSalva, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@DeleteMapping(value = "/deleteNotaFiscalCompraPorId/{id}")
	public ResponseEntity<?> deleteNotaFiscalCompraPorId(@PathVariable("id") Long id) { 
		
		
		notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id);/*Delete os filhos*/
		notaFiscalCompraRepository.deleteById(id); /*Deleta o pai*/
		
		return new ResponseEntity("Nota Fiscal Compra Removida",HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "/obterNotaFiscalCompra/{id}")
	public ResponseEntity<NotaFiscalCompra> obterNotaFiscalCompra(@PathVariable("id") Long id) throws LojaVirtualException { 
		
		NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);
		
		if (notaFiscalCompra == null) {
			throw new LojaVirtualException("Não encontrou Nota Fiscal com código: " + id);
		}
		
		return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);
	}
	
	
	
	
	@ResponseBody
	@GetMapping(value = "/obterNotaFiscalCompraDaVenda/{idvenda}")
	public ResponseEntity<List<NotaFiscalVenda>> obterNotaFiscalCompraDaVenda(@PathVariable("idvenda") Long idvenda) throws LojaVirtualException { 
		
		List<NotaFiscalVenda> notaFiscalCompra = notaFiscalVendaRepository.buscaNotaPorVenda(idvenda);
		
		if (notaFiscalCompra == null) {
			throw new LojaVirtualException("Não encontrou Nota Fiscal de venda com código da venda: " + idvenda);
		}
		
		return new ResponseEntity<List<NotaFiscalVenda>>(notaFiscalCompra, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "/obterNotaFiscalCompraDaVendaUnico/{idvenda}")
	public ResponseEntity<NotaFiscalVenda> obterNotaFiscalCompraDaVendaUnico(@PathVariable("idvenda") Long idvenda) throws LojaVirtualException { 
		
		NotaFiscalVenda notaFiscalCompra = notaFiscalVendaRepository.buscaNotaPorVendaUnica(idvenda);
		
		if (notaFiscalCompra == null) {
			throw new LojaVirtualException("Não encontrou Nota Fiscal de venda com código da venda: " + idvenda);
		}
		
		return new ResponseEntity<NotaFiscalVenda>(notaFiscalCompra, HttpStatus.OK);
	}
	
	
	
	@ResponseBody
	@GetMapping(value = "/buscarNotaFiscalPorDesc/{desc}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarNotaFiscalPorDesc(@PathVariable("desc") String desc) { 
		
		List<NotaFiscalCompra>  notaFiscalCompras = notaFiscalCompraRepository.buscaNotaDesc(desc.toUpperCase().trim());
		
		return new ResponseEntity<List<NotaFiscalCompra>>(notaFiscalCompras,HttpStatus.OK);
	}
	

}
