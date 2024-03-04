package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ObjetoPostCarneJuno implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Descrição da cobrança */
	private String description;

	/* Nome do comprador ou cliente */
	private String payerName;

	/* Fone do cliente comprador */
	private String payerPhone;

	/* Valor da compra ou parcela */
	private String totalAmount;

	/* Quantidade de parcelas */
	private String installments;

	/* Referncia para produto da loja ou codigo do produto */
	private String reference;
	
	private String payerCpfCnpj;
	
	private String email;
	
	private Long idVenda;
}
