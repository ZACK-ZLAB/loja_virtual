package br.com.zlab.loja_virtual.enums;

public enum StatusVendaLojaVirtual {
	
	FINALIZADA("Finalizada"),
	CANCELADA("Cancelada"),
	ABANDONOU_CARRINHO("Abandonou Carrinho");
	
	private String descricao = "";
	
	
	private StatusVendaLojaVirtual(String valor) {
		this.descricao = valor;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	
	@Override
	public String toString() {
		return this.descricao;
	}

}
