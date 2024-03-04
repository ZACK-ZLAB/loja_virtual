package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CriarWebHook implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;

	private List<String> eventTypes = new ArrayList<String>();
}
