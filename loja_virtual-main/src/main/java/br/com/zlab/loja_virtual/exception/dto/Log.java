package br.com.zlab.loja_virtual.exception.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Log {

    private boolean bStat;
    private String versao;
    private String tpAmb;
    private String cStat;
    private String verAplic;
    private String xMotivo;
    private String dhRecbto;
    private String cUF;
    private String nRec;
    private ArrayList<WebManiaProtNotaFiscal> aProt = new ArrayList<WebManiaProtNotaFiscal>();
    
    private String recibo;
}
