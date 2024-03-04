package br.com.zlab.loja_virtual.dto;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CobrancaJunoAPI implements Serializable {

	private static final long serialVersionUID = 1L;

	private Charge charge = new Charge();

	private Billing billing = new Billing();
}
