package br.com.zlab.loja_virtual.exception.dto;

import lombok.Data;

@Data
public class CobrancaGeradaSaasDiscount {

	  private Double value;
	  private String limitDate = null;
	  private Integer dueDateLimitDays;
	  private String type;
}
