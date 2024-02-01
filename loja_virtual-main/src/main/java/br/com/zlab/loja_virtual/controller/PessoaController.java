package br.com.zlab.loja_virtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zlab.loja_virtual.exception.handler.LojaVirtualException;
import br.com.zlab.loja_virtual.model.PessoaJuridica;
import br.com.zlab.loja_virtual.repository.PesssoaRepository;
import br.com.zlab.loja_virtual.service.PessoaUserService;
import jakarta.mail.MessagingException;

@RestController
public class PessoaController {
	
	@Autowired
	private PesssoaRepository pesssoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	
	@ResponseBody
	@PostMapping(value = "/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody PessoaJuridica pessoaJuridica) throws LojaVirtualException, MessagingException{
		
		if (pessoaJuridica == null) {
			throw new LojaVirtualException("Pessoa juridica nao pode ser NULL");
		}
		
		if (pessoaJuridica.getId() == null && pesssoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new LojaVirtualException("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		}
		if (pessoaJuridica.getId() == null && pesssoaRepository.existeInsEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new LojaVirtualException("Já existe Inscrição estadual cadastrado com o número: " + pessoaJuridica.getInscEstadual());
		}
			
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}

}
