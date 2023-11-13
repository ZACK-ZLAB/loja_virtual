package br.com.zlab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.zlab.model.Acesso;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {
	
	@Query("SELECT a FROM Acesso a WHERE upper(trim(a.descricao)) like %?1%")
	List<Acesso> buscarAcessoDesc(String desc);

}
