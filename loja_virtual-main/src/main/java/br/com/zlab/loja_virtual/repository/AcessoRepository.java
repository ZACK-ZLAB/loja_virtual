package br.com.zlab.loja_virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zlab.loja_virtual.model.Acesso;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {
	
	@Query("select a from Acesso a where upper(trim(a.descricao)) like %?1%")
	List<Acesso> buscarAcessoDesc(String desc);

}
