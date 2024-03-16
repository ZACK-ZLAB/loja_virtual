package br.com.zlab.loja_virtual.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.zlab.loja_virtual.model.Endereco;
import br.com.zlab.loja_virtual.model.Pessoa;
import lombok.Data;

@Data
public class VendaCompraLojaVirtualDTO {

	private Long id;

	private BigDecimal valorTotal = BigDecimal.ZERO;

	private BigDecimal valorDesc = BigDecimal.ZERO;

	private Pessoa pessoa;

	private Endereco cobranca;

	private Endereco entrega;

	private BigDecimal valorFrete = BigDecimal.ZERO;

	private List<ItemVendaDTO> itemVendaLoja = new ArrayList<ItemVendaDTO>();
}
