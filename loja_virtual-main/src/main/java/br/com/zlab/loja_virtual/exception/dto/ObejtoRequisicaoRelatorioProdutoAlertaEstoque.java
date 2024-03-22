package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ObejtoRequisicaoRelatorioProdutoAlertaEstoque implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeProduto = "";

	@NotEmpty(message = "Informa a data inicial")
	private String dataInicial;

	@NotEmpty(message = "Informa a data final")
	private String dataFinal;
	private String codigoNota = "";
	private String codigoProduto = "";
	private String valorVendaProduto = "";
	private String quantidadeComprada = "";
	private String codigoFornecedor = "";
	private String nomeFornecedor = "";
	private String dataCompra = "";
	private String qtdEstoque;
	private String qtdAlertaEstoque;
}
