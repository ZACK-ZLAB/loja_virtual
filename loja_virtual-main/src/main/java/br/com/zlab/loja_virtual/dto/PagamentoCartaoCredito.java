package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PagamentoCartaoCredito implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String chargeId= "";
	
	private BillingCartaoCredito billing = new BillingCartaoCredito();
	
	private CreditCardDetails creditCardDetails = new CreditCardDetails();

}
