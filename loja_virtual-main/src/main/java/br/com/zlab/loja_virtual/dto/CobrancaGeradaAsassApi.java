package br.com.zlab.loja_virtual.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
/**
 * main class of return cobranca Asaas API
 * 
 * @author zack
 */
public class CobrancaGeradaAsassApi {

	
	private String object;
    private Boolean hasMore;
    private Integer totalCount;
    private Integer limit;
    private Integer offset;
    private List<CobrancaGeradaAssasData> data = new ArrayList<CobrancaGeradaAssasData>();
}
