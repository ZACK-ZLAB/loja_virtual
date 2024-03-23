package br.com.zlab.loja_virtual.exception.dto;

import lombok.Data;

@Data
public class CobrancaApiAsaas {
	
	private String customer;
	private String billingType;
	private String dueDate;
	private float value;
	private String description;
	private String externalReference;

	private DiscountCobrancaAssas discount = new DiscountCobrancaAssas();
	private FineCobrancaAsaas fine = new FineCobrancaAsaas();
	private InterestCobrancaAsaas interest = new InterestCobrancaAsaas();
	
	private boolean postalService = false;



}
