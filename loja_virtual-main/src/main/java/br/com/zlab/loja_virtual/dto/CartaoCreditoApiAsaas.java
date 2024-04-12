package br.com.zlab.loja_virtual.dto;

import lombok.Data;

@Data
public class CartaoCreditoApiAsaas {

	private String holderName;
	private String number;
	private String expiryMonth;
	private String expiryYear;
	private String ccv;

}
