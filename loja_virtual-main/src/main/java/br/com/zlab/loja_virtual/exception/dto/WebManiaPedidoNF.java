package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class WebManiaPedidoNF implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer pagamento;
    private Integer presenca;
    private Integer modalidade_frete;
    private String frete;
    private String desconto;
    private String total;

}
