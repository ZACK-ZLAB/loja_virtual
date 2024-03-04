package br.com.zlab.loja_virtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zlab.loja_virtual.exception.handler.LojaVirtualException;
import br.com.zlab.loja_virtual.model.CupDesc;
import br.com.zlab.loja_virtual.model.MarcaProduto;
import br.com.zlab.loja_virtual.repository.CupDescontoRepository;
import jakarta.validation.Valid;

@RestController
public class CupDescontoController {

	@Autowired
	private CupDescontoRepository cupDescontoRepository;

	@ResponseBody
	@DeleteMapping(value = "/deleteCumpoDescontoPorId/{id}")
	public ResponseEntity<?> deleteCupomPorId(@PathVariable("id") Long id) { 
		
		cupDescontoRepository.deleteById(id);
		
		return new ResponseEntity<String>("Cupom de Desconto Removido",HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/obterCumpoDescontoPorId{id}")
	public ResponseEntity<CupDesc> obterCupDesc(@PathVariable("id") Long id) throws LojaVirtualException { 
		
		CupDesc cupom = cupDescontoRepository.findById(id).orElse(null);
		
		if (cupom == null) {
			throw new LojaVirtualException("Não encontrou o cupom de desconto com código: " + id);
		}
		
		return new ResponseEntity<CupDesc>(cupom,HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "/salvarCupomDesconto")
	public ResponseEntity<CupDesc> salvarCupDesc(@RequestBody @Valid CupDesc cupDesc)
			throws LojaVirtualException { /* Recebe o JSON e converte pra Objeto */

		CupDesc cupDesc2 = cupDescontoRepository.save(cupDesc);

		return new ResponseEntity<CupDesc>(cupDesc2, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/listaCupomDesc/{idEmpresa}")
	public ResponseEntity<List<CupDesc>> listaCupomDesc(@PathVariable("idEmpresa") Long idEmpresa) {

		return new ResponseEntity<List<CupDesc>>(cupDescontoRepository.cupDescontoPorEmpresa(idEmpresa), HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/listaCupomDesc")
	public ResponseEntity<List<CupDesc>> listaCupomDesc() {

		return new ResponseEntity<List<CupDesc>>(cupDescontoRepository.findAll(), HttpStatus.OK);
	}

}
