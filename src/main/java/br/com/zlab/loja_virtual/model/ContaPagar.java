package br.com.zlab.loja_virtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.zlab.loja_virtual.enums.StatusContaPagar;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "conta_pagar")
@SequenceGenerator(name = "seq_conta_pagar", sequenceName = "seq_conta_pagar", allocationSize = 1, initialValue = 1)
@Data
@EqualsAndHashCode
public class ContaPagar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta_pagar")
	private Long id;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private BigDecimal valorTotal;

	private BigDecimal valorDesconto;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusContaPagar status;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtVencimento;

	@Temporal(TemporalType.DATE)
	private Date dtPagamento;

	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;

	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_forn_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_forn_fk"))
	private Pessoa pessoa_fornecedor;
	
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private Pessoa empresa;
	
}
