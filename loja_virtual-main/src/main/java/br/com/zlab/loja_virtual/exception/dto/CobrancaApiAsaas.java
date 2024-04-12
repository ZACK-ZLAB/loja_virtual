package br.com.zlab.loja_virtual.exception.dto;

public class CobrancaApiAsaas {
	
	private String customer;
	private String billingType;
	private String dueDate;
	private float value;
	private String description;
	private String externalReference;
	private float installmentValue;
	private Integer installmentCount;
	

	private DiscountCobrancaAssas discount = new DiscountCobrancaAssas();
	private FineCobrancaAsaas fine = new FineCobrancaAsaas();
	private InterestCobrancaAsaas interest = new InterestCobrancaAsaas();
	
	private boolean postalService = false;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public float getInstallmentValue() {
		return installmentValue;
	}

	public void setInstallmentValue(float installmentValue) {
		this.installmentValue = installmentValue;
	}

	public Integer getInstallmentCount() {
		return installmentCount;
	}

	public void setInstallmentCount(Integer installmentCount) {
		this.installmentCount = installmentCount;
	}

	public DiscountCobrancaAssas getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountCobrancaAssas discount) {
		this.discount = discount;
	}

	public FineCobrancaAsaas getFine() {
		return fine;
	}

	public void setFine(FineCobrancaAsaas fine) {
		this.fine = fine;
	}

	public InterestCobrancaAsaas getInterest() {
		return interest;
	}

	public void setInterest(InterestCobrancaAsaas interest) {
		this.interest = interest;
	}

	public boolean isPostalService() {
		return postalService;
	}

	public void setPostalService(boolean postalService) {
		this.postalService = postalService;
	}
	
}
