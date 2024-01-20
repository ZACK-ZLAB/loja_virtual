package br.com.zlab.loja_virtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import br.com.zlab.loja_virtual.model.PessoaJuridica;
import br.com.zlab.loja_virtual.repository.PesssoaRepository;
import br.com.zlab.loja_virtual.service.PessoaUserService;

@RestController
public class PessoaController {
	
	@Autowired
	private PesssoaRepository pesssoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	/*end-point é microsservicos é um API*/
	@ResponseBody
	@PostMapping(value = "/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody PessoaJuridica pessoaJuridica){
		Preconditions.checkNotNull(pessoaJuridica, 
				"Pessoa Juridica não pode ser null");
		
		Preconditions.checkArgument(pessoaJuridica.getCnpj() != null, 
				"Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}

}
