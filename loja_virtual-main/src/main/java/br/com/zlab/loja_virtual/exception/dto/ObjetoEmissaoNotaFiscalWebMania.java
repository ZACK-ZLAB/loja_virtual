package br.com.zlab.loja_virtual.exception.dto;

import lombok.Data;

@Data
public class ObjetoEmissaoNotaFiscalWebMania {

    private String uuid;
    private String status;
    private String motivo;
    private String nfe;
    private String serie;
    private String chave;
    private String modelo;
    private String xml;
    private String danfe;
    private String danfe_simples;
    private String danfe_etiqueta;
    private Log logObject;
    private float recibo;
}
