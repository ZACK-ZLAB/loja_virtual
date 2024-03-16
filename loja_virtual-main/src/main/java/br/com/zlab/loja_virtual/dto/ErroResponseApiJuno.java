package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ErroResponseApiJuno implements Serializable {

	private static final long serialVersionUID = 1L;

	private String timestamp;
	
	private String status;
	
	private String error;
	
	private String path;
	
	private List<DetailsErroJunoApi> details = new ArrayList<DetailsErroJunoApi>();

	public String listaErro() {
		String retorno = "";
		
		for (DetailsErroJunoApi detailsErroJunoApi : details) {
			
			retorno += detailsErroJunoApi.getMessage() + "\n";
		}
		return retorno;
	}
}
