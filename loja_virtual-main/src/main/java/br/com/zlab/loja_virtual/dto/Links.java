package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;

public class Links implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Self self = new Self();
	
	
	public void setSelf(Self self) {
		this.self = self;
	}
	
	public Self getSelf() {
		return self;
	}

}
