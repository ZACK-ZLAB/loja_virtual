package br.com.zlab.loja_virtual.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CobrancaGeradaAssasData {

	private String object;
	private String id;
	private String dateCreated;
	private String customer;
	private String paymentLink = null;
	private float value;
	private float netValue;
	private String originalValue = null;
	private float interestValue;
	private String description = null;
	private String billingType;
	private String confirmedDate;
	private String pixTransaction;
	private String pixQrCodeId;
	private String status;
	private String dueDate;
	private String originalDueDate;
	private String paymentDate;
	private String clientPaymentDate;
	private String installmentNumber = null;
	private String invoiceUrl;
	private String invoiceNumber;
	private String externalReference = null;
	private boolean deleted;
	private boolean anticipated;
	private boolean anticipable;
	private String creditDate;
	private String estimatedCreditDate = null;
	private String transactionReceiptUrl;
	private String nossoNumero;
	private String bankSlipUrl = null;
	private String lastInvoiceViewedDate;
	private String lastBankSlipViewedDate = null;
	private boolean postalService = false;
	private CobrancaoGeradaSaasDiscount discount = new CobrancaoGeradaSaasDiscount();
	private CobrancaoGeradaSaasFine fine = new CobrancaoGeradaSaasFine();
	private CobrancaoGeradaSaasInterest interest= new CobrancaoGeradaSaasInterest();


}
