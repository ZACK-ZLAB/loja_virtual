package br.com.zlab.loja_virtual.dto;

import lombok.Data;

@Data
public class CobrancaApiAsaasCartao {

	private String customer;
    private String billingType;
    private float value;
    private String dueDate;
    private String description;
    private String externalReference;
    private Integer installmentCount;
    private float installmentValue;
   
    
    private DiscontCobrancaAsaas discount = new DiscontCobrancaAsaas();
    private FineCobrancaAsaas fine = new FineCobrancaAsaas();
    private InterestCobrancaAsass interest = new InterestCobrancaAsass();
    private boolean postalService = false;
    
    private CartaoCreditoApiAsaas creditCard = new CartaoCreditoApiAsaas(); 
    private CartaoCreditoAsaasHolderInfo creditCardHolderInfo = new CartaoCreditoAsaasHolderInfo();
}
