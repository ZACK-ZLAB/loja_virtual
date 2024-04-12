package br.com.zlab.loja_virtual.dto;

import lombok.Data;

@Data
public class CobrancaApiAsaas {
    private String customer;
    private String billingType;
    private String dueDate;
    private float value;
    private String description;
    private String externalReference;
    private float installmentValue;
    private Integer installmentCount;
    
    private DiscontCobrancaAsaas discount = new DiscontCobrancaAsaas();
    private FineCobrancaAsaas fine = new FineCobrancaAsaas();
    private InterestCobrancaAsass interest = new InterestCobrancaAsass();
    private boolean postalService = false;
}

