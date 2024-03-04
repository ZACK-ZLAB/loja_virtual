package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Payments implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String chargeId;
	private String date;
	private String releaseDate;
	private String amount;
	private String fee;
	private String type;
	private String status;
	private String transactionId;
	private String failReason;

}
