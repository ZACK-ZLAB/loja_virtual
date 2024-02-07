package br.com.zlab.loja_virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceContagemAcessoApi {
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	/*realiza a contagem dos acessos realizados a esse end point*/
	public void atualizaAcessoEndPointPF() {
        jdbcTemplate.update("UPDATE tabela_acesso_end_point SET qtd_acesso_end_point = qtd_acesso_end_point + 1 WHERE nome_end_point = 'END-POINT-NOME-PESSOA-FISICA'");
    }
}
