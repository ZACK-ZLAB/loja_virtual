package br.com.zlab.loja_virtual.security;

import java.util.Date;

import br.com.zlab.loja_virtual.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String login;
	private String senha;
	private Date dataAtualSenha;
	private Pessoa pessoa;
	private Pessoa empresa;
}