package br.com.zlab.repository.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zlab.model.Acesso;
import br.com.zlab.repository.AcessoRepository;


@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso save(Acesso acesso) {
		
		/*Aqui podemos fazer qualque tipo de validação*/
		
		
		return acessoRepository.save(acesso);
	}
	
	public void delete(Acesso acesso) {
		
		acessoRepository.deleteById(acesso.getId());
	}
	
}
