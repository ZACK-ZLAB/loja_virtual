package br.com.zlab.loja_virtual.enums;

public enum TipoPessoa {
	
	JURIDICA("Jurídica"),
	JURIDICA_FORNECEDOR("Jurídica e Fornecedor"),
	FISICA("Física");
	
	private String descricao;
	
	
	private TipoPessoa(String descrica) {
		this.descricao = descrica;
		
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	
	
	@Override
	public String toString() {
		return this.descricao;
	}

}
