package br.com.zlab.loja_virtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.zlab.loja_virtual.controller.PessoaController;
import br.com.zlab.loja_virtual.enums.TipoEndereco;
import br.com.zlab.loja_virtual.exception.handler.LojaVirtualException;
import br.com.zlab.loja_virtual.model.Endereco;
import br.com.zlab.loja_virtual.model.PessoaFisica;
import br.com.zlab.loja_virtual.model.PessoaJuridica;
import br.com.zlab.loja_virtual.repository.PesssoaRepository;
import jakarta.mail.MessagingException;
import junit.framework.TestCase;


@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaController pessoaController;
	
	@Autowired
	private PesssoaRepository pesssoaRepository;

	@Test
	public void testCadPessoaJuridica() throws LojaVirtualException, MessagingException{

		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Alex fernando");
		pessoaJuridica.setEmail("segundozack@gmail.com");
		pessoaJuridica.setTelefone("45999795800");
		pessoaJuridica.setInscEstadual("65556565656665");
		pessoaJuridica.setInscMunicipal("55554565656565");
		pessoaJuridica.setNomeFantasia("54556565665");
		pessoaJuridica.setRazaoSocial("4656656566");

		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("556556565");
		endereco1.setComplemento("Casa cinza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogra("Av. são joao sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		endereco1.setCidade("Curitiba");
		
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Maracana");
		endereco2.setCep("7878778");
		endereco2.setComplemento("Andar 4");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogra("Av. maringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("PR");
		endereco2.setCidade("Curitiba");
		
		pessoaJuridica.getEnderecos().add(endereco2);
		pessoaJuridica.getEnderecos().add(endereco1);

		pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();
		
		/*validation can be make here*/
		/*if generete a person*/
		assertEquals(true, pessoaJuridica.getId() > 0 );
		/*if generete addresses*/
		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		/*test if you have 2 address*/
		assertEquals(2, pessoaJuridica.getEnderecos().size());

	}
	
	@Test
	public void testCadPessoaFisica() throws LojaVirtualException, MessagingException {
		
		PessoaJuridica pessoaJuridica =  pesssoaRepository.existeCnpjCadastrado("5114558434584131258");
		

		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("713.482.980-49");
		pessoaFisica.setNome("Alex fernando");
		pessoaFisica.setEmail("segundozack@gmail.com");
		pessoaFisica.setTelefone("45999795800");
		pessoaFisica.setEmpresa(pessoaJuridica);
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("556556565");
		endereco1.setComplemento("Casa cinza");
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaFisica);
		endereco1.setRuaLogra("Av. são joao sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		endereco1.setCidade("Curitiba");
		endereco1.setEmpresa(pessoaJuridica);
		
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Maracana");
		endereco2.setCep("7878778");
		endereco2.setComplemento("Andar 4");
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaFisica);
		endereco2.setRuaLogra("Av. maringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("PR");
		endereco2.setCidade("Curitiba");
		endereco2.setEmpresa(pessoaJuridica);
		
		pessoaFisica.getEnderecos().add(endereco2);
		pessoaFisica.getEnderecos().add(endereco1);

		pessoaFisica = pessoaController.salvarPf(pessoaFisica).getBody();
		
		assertEquals(true, pessoaFisica.getId() > 0 );
		
		for (Endereco endereco : pessoaFisica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaFisica.getEnderecos().size());

	}

}
