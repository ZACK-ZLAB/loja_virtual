package br.com.zlab.loja_virtual.dto;

import lombok.Data;

@Data
public class AddressCartaoCredito {

	private String street = "";
	private String number = "";
	private String complement = "";
	private String neighborhood = "";
	private String city = "";
	private String state = "";
	private String postCode = "";
	
	
}
