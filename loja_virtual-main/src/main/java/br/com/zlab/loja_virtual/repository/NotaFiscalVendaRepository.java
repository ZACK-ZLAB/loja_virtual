package br.com.zlab.loja_virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zlab.loja_virtual.model.NotaFiscalVenda;

@Repository
public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {
	
	@Query(value = "select n from NotaFiscalVenda n where n.vendaCompraLojaVirtual.id = ?1")
	List<NotaFiscalVenda> buscaNotaPorVenda(Long idVenda);
	
	
	@Query(value = "select n from NotaFiscalVenda n where n.vendaCompraLojaVirtual.id = ?1")
	NotaFiscalVenda buscaNotaPorVendaUnica(Long idVenda);
}
