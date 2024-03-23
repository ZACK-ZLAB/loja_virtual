package br.com.zlab.loja_virtual.exception.dto;

import lombok.Data;

@Data
public class ClienteAsaasApiPagamento {

	private String name;
	private String email;
	private String cpfCnpj;
	private String phone;
}
