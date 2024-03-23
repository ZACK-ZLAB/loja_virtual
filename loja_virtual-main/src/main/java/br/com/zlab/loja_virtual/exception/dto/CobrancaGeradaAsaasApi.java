package br.com.zlab.loja_virtual.exception.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Classe pricipal retorno da cobranca API Asaas
 * @author zack_
 */
@Data
public class CobrancaGeradaAsaasApi {

	private String object;
	private boolean hasMore;
	private Integer totalCount;
	private Integer limit;
	private Integer offset;
	
	private List<CobrancaGeradaAsaasData> data = new ArrayList<CobrancaGeradaAsaasData>();
}
