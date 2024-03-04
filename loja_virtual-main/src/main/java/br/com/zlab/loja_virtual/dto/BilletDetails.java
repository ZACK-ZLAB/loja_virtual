package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BilletDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bankAccount;
	private String ourNumber;
	private String barcodeNumber;
	private String portfolio;
}
