package br.com.zlab.loja_virtual.dto;

import lombok.Data;

@Data
public class CobrancaoGeradaSaasDiscount {

	private Double value;
	private String limitDate;
	private Integer dueDateLimitDays;
	private String type;
}
