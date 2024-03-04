package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Links;

import lombok.Data;

@Data
public class ConteudoBoletoJuno implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String code;
	private String reference;
	private String dueDate;
	private String link;
	private String checkoutUrl;
	private String installmentLink;
	private String payNumber;
	private String amount;
	private String status;

	private BilletDetails billetDetails = new BilletDetails();

	private List<Payments> payments = new ArrayList<Payments>();

	private Pix pix = new Pix();

	private List<Links> _links = new ArrayList<Links>();
}
