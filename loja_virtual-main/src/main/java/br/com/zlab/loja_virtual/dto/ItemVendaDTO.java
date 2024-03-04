package br.com.zlab.loja_virtual.dto;

import br.com.zlab.loja_virtual.model.Produto;
import lombok.Data;

@Data
public class ItemVendaDTO {

	private Double quantidade;

	private Produto produto;
}
