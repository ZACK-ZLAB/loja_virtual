package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponseCadastrado;

public class LeadCampanhaGetResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String dayOfCycle;
    private String scoring;
    private String ipAddress;

    private LeadCampanhaGetResponseCadastrado campaign = new LeadCampanhaGetResponseCadastrado();
}

