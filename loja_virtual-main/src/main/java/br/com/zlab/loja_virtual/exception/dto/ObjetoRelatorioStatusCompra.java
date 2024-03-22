package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ObjetoRelatorioStatusCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Informa a data inicial")
	private String dataInicial;

	@NotEmpty(message = "Informa a data final")
	private String dataFinal;

	private String codigoProduto ="";
	private String nomeProduto ="";
	private String emailCliente ="";
	private String foneCliente ="";
	private String valorVendaProduto ="";
	private String codigoCliente ="";
	private String nomeCliente ="";
	private String qtdEstoque ="";
	private String codigoVenda ="";
	private String statusVenda ="";
}
