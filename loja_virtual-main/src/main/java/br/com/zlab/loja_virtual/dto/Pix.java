package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Pix implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String payloadInBase64;
	private String imageInBase64;

}
