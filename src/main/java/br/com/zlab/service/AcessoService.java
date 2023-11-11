package br.com.zlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zlab.model.Acesso;
import br.com.zlab.repository.AcessoRepository;

@Service
public class AcessoService {

	@Autowired
	private AcessoRepository acessoRepository;

	public Acesso save(Acesso acesso) {
		
		/*any validation*/
		
		return acessoRepository.save(acesso);
	}
}
