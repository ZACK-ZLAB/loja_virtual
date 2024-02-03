package br.com.zlab.loja_virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zlab.loja_virtual.model.PessoaFisica;

@Repository
public interface PesssoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {
	
	
	@Query(value = "select pf from PessoaFisica pf where upper(trim(pf.nome)) like %?1%")
	public List<PessoaFisica> pesquisaPorNomePF(String nome);
	
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public List<PessoaFisica> pesquisaPorCpfPF(String cpf);
	
}
