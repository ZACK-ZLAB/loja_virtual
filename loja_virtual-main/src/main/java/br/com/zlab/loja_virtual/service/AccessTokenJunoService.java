package br.com.zlab.loja_virtual.service;

import org.springframework.stereotype.Service;

import br.com.zlab.loja_virtual.model.AccessTokenJunoAPI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Service
public class AccessTokenJunoService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public AccessTokenJunoAPI buscaTokenAtivo() {
		
		try {
		
		   AccessTokenJunoAPI accessTokenJunoAPI = 
				  (AccessTokenJunoAPI) entityManager
				  .createQuery("select a from AccessTokenJunoAPI a ")
		          .setMaxResults(1).getSingleResult();
		   
		   return accessTokenJunoAPI;
		}catch (NoResultException e) {
			return null;
		}
		
		
	}

}
