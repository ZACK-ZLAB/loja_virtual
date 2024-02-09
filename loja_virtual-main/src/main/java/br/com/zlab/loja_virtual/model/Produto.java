package br.com.zlab.loja_virtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
@Data
@EqualsAndHashCode
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;

	@NotNull(message = "O tipo da unidade deve ser informado")
	@Column(nullable = false)
	private String tipoUnidade;

	@Size(min = 10, message = "Nome do produto deve ter mais de 10 letras")
	@NotNull(message = "Nome do produto deve ser informado")
	@Column(nullable = false)
	private String nome;

	@NotNull(message = "Descrição do produto deve ser informada")
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;

	@Column(columnDefinition = "text", length = 2000, nullable = false)
	private String descricao;

	/** Nota item nota produto - ASSOCIAR **/

	@NotNull(message = "Peso deve ser informado")
	@Column(nullable = false)
	private Double peso; /* 1000.55 G */

	@NotNull(message = "Largura deve ser informado")
	@Column(nullable = false)
	private Double largura;

	@NotNull(message = "Altura deve ser informado")
	@Column(nullable = false)
	private Double altura;

	@NotNull(message = "Profundidade")
	@Column(nullable = false)
	private Double profundidade;

	@NotNull(message = "Valor de venda deve ser informado")
	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;

	@Column(nullable = false)
	private Integer qtdEstoque = 0;

	private Integer qtdeAlertaEstoque = 0;

	private String linkYoutube;

	private Boolean alertaQtdeEstoque = Boolean.FALSE;

	private Integer qtdeClique = 0;

	@NotNull(message = "A empresa responsável deve ser informada")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "A Categoria do Produto deve ser informada")
	@ManyToOne(targetEntity = CategoriaProduto.class)
	@JoinColumn(name = "categoria_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoria_produto_id_fk"))
	private CategoriaProduto categoriaProduto = new CategoriaProduto();
	
	@NotNull(message = "A Marca do Produto deve ser informada")
	@ManyToOne(targetEntity = MarcaProduto.class)
	@JoinColumn(name = "marca_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "marca_produto_id_fk"))
	private MarcaProduto marcaProduto = new MarcaProduto();
	
	@OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ImagemProduto> imagens = new ArrayList<ImagemProduto>();

}
