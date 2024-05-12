package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class WebManiaNotaFiscalEletronica implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ID;
    private String url_notificacao;
    private Integer operacao;
    private String natureza_operacao;
    private String modelo;
    private Integer finalidade;
    private Integer ambiente;
    
    private WebManiaClienteNF cliente = new WebManiaClienteNF();
    private List<WebManiaProdutoNF> produtos = new ArrayList<WebManiaProdutoNF>();
    private WebManiaPedidoNF pedido = new WebManiaPedidoNF();

}
