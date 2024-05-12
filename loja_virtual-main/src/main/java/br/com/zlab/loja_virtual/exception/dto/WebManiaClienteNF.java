package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class WebManiaClienteNF implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cpf;
    private String nomeCompleto;
    private String endereco;
    private String complemento;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String telefone;
    private String email;
}
