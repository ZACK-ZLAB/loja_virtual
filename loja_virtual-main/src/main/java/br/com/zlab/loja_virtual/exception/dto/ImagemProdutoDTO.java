package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import lombok.Data;
@Data
public class ImagemProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String imagemOriginal;

	private String imagemMiniatura;

	private Long produto;

	private Long empresa;
}
