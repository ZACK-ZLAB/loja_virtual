package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@Data
public class ObjetoErroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String error;
	private String code;
	private List<ValidationErrorDTO> validationErrors;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
