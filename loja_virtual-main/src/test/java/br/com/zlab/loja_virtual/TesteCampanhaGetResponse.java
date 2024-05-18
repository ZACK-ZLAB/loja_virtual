package br.com.zlab.loja_virtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.zlab.loja_virtual.dto.ObjetoFromFieldIdGetResponse;
import br.com.zlab.loja_virtual.exception.dto.CampanhaGetResponse;
import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponse;
import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponseCadastrado;
import br.com.zlab.loja_virtual.service.ServiceGetResponseEmailMarketing;
import junit.framework.TestCase;

@Profile("devp")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteCampanhaGetResponse extends TestCase {
	@Autowired
	private ServiceGetResponseEmailMarketing serviceGetResponseEmailMarketing;
	
	@Test
	public void testCarregaCampanhaGetResponse() throws Exception {

		List<CampanhaGetResponse> list = serviceGetResponseEmailMarketing.carregaListaCampanhaGetResponse();

	    for (CampanhaGetResponse campanhaGetResponse : list) {
	        System.out.println(campanhaGetResponse);
	        System.out.println("----------------");
	    }
	}
	
	@Test
	public void testCriaLead() throws Exception {

	    LeadCampanhaGetResponse lead = new LeadCampanhaGetResponse();
	    lead.setName("Zlab teste api");
	    lead.setEmail("zacarias1992.12.19@gmail.com");

	    LeadCampanhaGetResponseCadastrado campanha = new LeadCampanhaGetResponseCadastrado();
	    campanha.setCampaignId("jSz0Y");
	    lead.setCampaign(campanha);

	    String retorno = serviceGetResponseEmailMarketing.criaLeadApiGetResponse(lead);
	    
	    System.out.println(retorno);
	}
	
	
	@Test
	public void testEnviaEmailPorAPI() throws Exception {
	    String retorno = serviceGetResponseEmailMarketing.enviaEmailApiGetResponse("jSz0Y", "Teste de e-mail", "<html><body>texto do email</body></html>");
	    System.out.println(retorno);
	}


	
	@Test
	public void testBuscaFromFielId() throws Exception {

	    List<ObjetoFromFieldIdGetResponse> fieldIdGetResponses = serviceGetResponseEmailMarketing.listaFromFieldId();

	    for (ObjetoFromFieldIdGetResponse objetoFromFieldIdGetResponse : fieldIdGetResponses) {
	        System.out.println(objetoFromFieldIdGetResponse);
	    }

	}

}
