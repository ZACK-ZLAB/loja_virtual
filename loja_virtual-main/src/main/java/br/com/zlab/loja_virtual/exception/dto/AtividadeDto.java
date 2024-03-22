package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AtividadeDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String text;

	private String code;

}
