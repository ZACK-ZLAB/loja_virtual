package br.com.zlab.loja_virtual.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "boleto_juno")
@SequenceGenerator(name = "seq_boleto_juno", sequenceName = "seq_boleto_juno", allocationSize = 1, initialValue = 1)
@Data
@EqualsAndHashCode
public class BoletoJuno implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_boleto_juno")
	private Long id;

	/* Código de controle do boleto */
	private String code = "";

	/* Imprime o boleto completo com todas as parcelas */
	private String link;

	/*
	 * Mostra um telinha de checkout da Juno com os boleto, pix e cartão pagos ou
	 * vencidos
	 */
	private String checkoutUrl = "";

	private boolean quitado = false;

	private String dataVencimento = "";

	private BigDecimal valor = BigDecimal.ZERO;

	private Integer recorrencia = 0;

	/* Id controle do boleto para poder cancelar pela api */
	private String idChrBoleto = "";

	/* Link da parcela do boleto */
	private String installmentLink = "";

	private String IdPix = "";

	@Column(columnDefinition = "text")
	private String payloadInBase64 = "";

	@Column(columnDefinition = "text")
	private String imageInBase64 = "";

	private String chargeICartao = "";

	@ManyToOne
	@JoinColumn(name = "venda_compra_loja_virt_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "venda_compra_loja_virt_fk"))
	private VendaCompraLojaVirtual vendaCompraLojaVirtual;

	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
}
