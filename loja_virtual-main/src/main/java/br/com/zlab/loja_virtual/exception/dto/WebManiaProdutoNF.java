package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class WebManiaProdutoNF implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String codigo;
    private String ncm;
    private String cest;
    private Integer quantidade;
    private String unidade;
    private String peso;
    private Integer origem;
    private String subtotal;
    private String total;
    private String classe_imposto;
}
