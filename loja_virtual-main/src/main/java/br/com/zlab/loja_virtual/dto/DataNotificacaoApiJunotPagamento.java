package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/*Objeto principal recebimento api juno boleto pix - webhook*/
@Data
public class DataNotificacaoApiJunotPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private String eventId;
	private String eventType;
	private String timestamp;

	private List<AttibutesNotificaoPagaApiJuno> data = new ArrayList<AttibutesNotificaoPagaApiJuno>();
}
