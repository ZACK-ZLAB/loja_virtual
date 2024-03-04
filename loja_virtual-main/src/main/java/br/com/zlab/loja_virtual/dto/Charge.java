package br.com.zlab.loja_virtual.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Charge implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pixKey;
	private boolean pixIncludeImage = true;
	private String description;
	private List<String> references = new ArrayList<String>();
	private Float amount;
	private String dueDate;
	private Integer installments;
	private Integer maxOverdueDays;
	private BigDecimal fine;
	private BigDecimal interest;
	private List<String> paymentTypes = new ArrayList<String>();
	
}
