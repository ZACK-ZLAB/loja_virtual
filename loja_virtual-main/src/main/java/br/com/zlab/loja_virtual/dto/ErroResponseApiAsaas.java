package br.com.zlab.loja_virtual.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ErroResponseApiAsaas {

	private List<ObjetoErroResponseApiAsaas> errors = new ArrayList<ObjetoErroResponseApiAsaas>();
	
	public String listaErros() {
	    StringBuilder builder = new StringBuilder();
	    for (ObjetoErroResponseApiAsaas error : errors) {
	        builder.append(error.getDescription()).append(" - Code: ").append(error.getCode()).append("\n");
	    }
	    return builder.toString();
	}

}
