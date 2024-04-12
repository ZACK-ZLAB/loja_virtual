package br.com.zlab.loja_virtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.zlab.loja_virtual.dto.ObjetoPostCarneJuno;
import br.com.zlab.loja_virtual.service.ServiceJunoBoleto;

@Profile("devp")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestApiAsaas {

	@Autowired
	private ServiceJunoBoleto serviceAsaas;
	
	@Test
	public void testGerarCarnerApiAsaas() throws Exception {
		ObjetoPostCarneJuno dados = new ObjetoPostCarneJuno();
		dados.setEmail("junior88464.js@gmail.com");
		dados.setPayerName("zacarias Santos");
		dados.setPayerCpfCnpj("03620659346");
		dados.setPayerPhone("89999944007");
		dados.setIdVenda(1L);
		
		String retorno = serviceAsaas.gerarCarneApiAsaas(dados);
				
		System.out.println(retorno);
	}
	
	
	@Test
	public void testcriarChavePixAsaas() throws Exception {
		String chaveAPI = serviceAsaas.criarChavePixAsaas();
		
		System.out.println("Chave Asaas API" + chaveAPI);
	}
	
	@Test
	public void buscaClientePessoaapiAsaas() throws Exception {
		ObjetoPostCarneJuno dados = new ObjetoPostCarneJuno();
		dados.setEmail("junior88464.js@gmail.com");
		dados.setPayerName("zacarias Santos");
		dados.setPayerCpfCnpj("03620659346");
		dados.setPayerPhone("89999944007");
		
		String customer_id = serviceAsaas.buscaClientePessoaApiAsaas(dados);
		
		assertEquals("cus_000080912482", customer_id);
	}
}
