package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoriaProdutoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nomeDesc;

	private String empresa;


}
