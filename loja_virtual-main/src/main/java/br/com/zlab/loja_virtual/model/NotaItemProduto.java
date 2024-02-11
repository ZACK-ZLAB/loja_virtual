package br.com.zlab.loja_virtual.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "nota_item_produto")
@SequenceGenerator(name = "seq_nota_item_produto", sequenceName = "seq_nota_item_produto", allocationSize = 1, initialValue = 1)
@Data
@EqualsAndHashCode
public class NotaItemProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_item_produto")
	private Long id;

	//@Size(min = 1, message = "Informe a quantidade do produto")
		@Column(nullable = false)
		private Double quantidade;

		@ManyToOne
		@JoinColumn(name = "nota_fiscal_compra_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_fiscal_compra_fk"))
		private NotaFiscalCompra notaFiscalCompra;

		@ManyToOne
		@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
		private Produto produto;
		
		
		@ManyToOne(targetEntity = PessoaJuridica.class)
		@JoinColumn(name = "empresa_id", nullable = false, 
		foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
		private PessoaJuridica empresa;

}
