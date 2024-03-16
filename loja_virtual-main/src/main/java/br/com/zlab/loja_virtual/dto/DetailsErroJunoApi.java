package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DetailsErroJunoApi implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String field;
	
	private String message;
	
	private String errorCode;

}
