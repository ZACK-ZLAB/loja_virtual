package br.com.zlab.loja_virtual.exception.handler;

public class LojaVirtualException extends Exception {
	private static final long serialVersionUID = 1L;

	public LojaVirtualException(String msgErro) {
		super(msgErro);
	}

}
