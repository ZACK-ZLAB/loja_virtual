package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RetornoPagamentoCartaoJuno implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String transactionId;
	
	private String installments;
	
	private List<PaymentsCartaoCredito> payments = new ArrayList<PaymentsCartaoCredito>();
	
	private List<Links> _links = new ArrayList<Links>();

}
