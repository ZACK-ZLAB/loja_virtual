package br.com.zlab.loja_virtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.zlab.loja_virtual.exception.dto.WebManiaClienteNF;
import br.com.zlab.loja_virtual.exception.dto.WebManiaNotaFiscalEletronica;
import br.com.zlab.loja_virtual.exception.dto.WebManiaPedidoNF;
import br.com.zlab.loja_virtual.exception.dto.WebManiaProdutoNF;
import br.com.zlab.loja_virtual.service.WebManiaNotaFiscalService;
import junit.framework.TestCase;

@Profile("devp")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteNotaFiscal extends TestCase {

    @Autowired
    private WebManiaNotaFiscalService webManiaNotaFiscalService;

    @Test
    public void testeEmissaoNota() throws Exception {
        WebManiaNotaFiscalEletronica webManiaNotaFiscalEletronica = new WebManiaNotaFiscalEletronica();

        webManiaNotaFiscalEletronica.setID("1");
        webManiaNotaFiscalEletronica.setUrl_notificacao(""); /* WebHook */
        webManiaNotaFiscalEletronica.setOperacao(1);
        webManiaNotaFiscalEletronica.setNatureza_operacao("Venda de celular Iphone 13");
        webManiaNotaFiscalEletronica.setModelo("1");
        webManiaNotaFiscalEletronica.setFinalidade(1);
        webManiaNotaFiscalEletronica.setAmbiente(2); /* Homologação */
        
        /*Client data */
        WebManiaClienteNF cliente = new WebManiaClienteNF();

        cliente.setBairro("JD Dias 1");
        cliente.setCep("87025758");
        cliente.setCidade("Maringá");
        cliente.setComplemento("NA");
        cliente.setCpf("05916564937");
        cliente.setEmail("alex.fernando.egidio@gmail.com");
        cliente.setEndereco("Pioneiro Antonio de Ganello");
        cliente.setNumero("356");
        cliente.setTelefone("45999795800");
        cliente.setUf("PR");
        cliente.setNomeCompleto("José Zacarias dos Santos Junior");

        webManiaNotaFiscalEletronica.setCliente(cliente);
        
        /* Product Data */
        WebManiaProdutoNF produto = new WebManiaProdutoNF();

        produto.setNome("Iphone 13");
        produto.setCodigo("1111");
        produto.setNcm("6109.10.00");
        produto.setCest("28.038.00");
        produto.setQuantidade(1);
        produto.setUnidade("UN");
        produto.setPeso("0.800");
        produto.setOrigem(0);
        produto.setSubtotal("5500");
        produto.setTotal("5500");
        produto.setClasse_imposto("REF1000");

        webManiaNotaFiscalEletronica.getProdutos().add(produto);
        
        WebManiaPedidoNF pedidoNF = new WebManiaPedidoNF();

        pedidoNF.setPagamento(0);/*a vista*/
        pedidoNF.setPresenca(2);/*pela internet*/
        pedidoNF.setModalidade_frete(0);
        pedidoNF.setFrete("80");/*deve bater com o total do itens, total+frete=total dos itens*/
        pedidoNF.setDesconto("80");
        pedidoNF.setTotal("5420");

        webManiaNotaFiscalEletronica.setPedido(pedidoNF);


        String retorno = webManiaNotaFiscalService.emitirNotaFiscal(webManiaNotaFiscalEletronica);
        System.out.println("---->> Retorno Emissão nota fiscal: " + retorno);
    }
    
    @Test
    public void cancelNota() throws Exception {
        String retorno = webManiaNotaFiscalService.cancelarNotaFiscal(
            "93d9fd23-2389-4f50-b57f-b873471eda2c", "cancelamento teste"/*uuid and reason*/
        );

        System.out.println("--->> Retorno cancelamento nota fiscal: " + retorno);
    }
    
    @Test
    public void consultarNota() throws Exception {
        String retorno = webManiaNotaFiscalService.consultarNotaFiscal("93d9fd23-2389-4f50-b57f-b873471eda2c");

        System.out.println("--->> Retorno consulta nota fiscal: " + retorno);
    }

}
