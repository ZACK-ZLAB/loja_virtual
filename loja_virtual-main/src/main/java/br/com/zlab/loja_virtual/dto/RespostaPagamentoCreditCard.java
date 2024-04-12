package br.com.zlab.loja_virtual.dto;

import lombok.Data;

@Data
public class RespostaPagamentoCreditCard {

	private String creditCardNumber;
	private String creditCardBrand;
	private String creditCardToken;
}
