package br.com.zlab.loja_virtual.dto;

import lombok.Data;

@Data
public class ValidationErrorDTO {
    private String field;
    private String message;
}
