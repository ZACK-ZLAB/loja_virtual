package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BillingCartaoCredito implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String email = "";
	
	private boolean delayed = false;
	
	private AddressCartaoCredito address = new AddressCartaoCredito();

}
