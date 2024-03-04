package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Billing implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String document;
	private String email;

	private String phone;

	private boolean notify = true;
}
