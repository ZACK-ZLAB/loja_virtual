package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ObjetoEstornoNotaFiscalWebMania implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String chave;
    private String natureza_operacao;
    private String codigo_cfop;
    private String ambiente;

}
