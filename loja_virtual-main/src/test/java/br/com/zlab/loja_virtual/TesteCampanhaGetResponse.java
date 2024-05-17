package br.com.zlab.loja_virtual;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.zlab.loja_virtual.dto.NewsLetterGetResponse;
import br.com.zlab.loja_virtual.exception.dto.CampanhaGetResponse;
import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponse;
import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponseCadastrado;
import br.com.zlab.loja_virtual.service.HostIgnoringCliente;
import br.com.zlab.loja_virtual.service.ServiceGetResponseEmailMarketing;
import br.com.zlab.loja_virtual.util.ApiTokenIntegracao;
import jakarta.ws.rs.core.MediaType;
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
	    lead.setEmail("zlabtestes@gmail.com");

	    LeadCampanhaGetResponseCadastrado campanha = new LeadCampanhaGetResponseCadastrado();
	    campanha.setCampaignId("jSz0Y");
	    lead.setCampaign(campanha);

	    String json = new ObjectMapper().writeValueAsString(lead);

	    Client client = new HostIgnoringCliente(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringCliente();
	    WebResource webResource = client.resource(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE + "contacts");

	    ClientResponse clientResponse = webResource
	        .accept(MediaType.APPLICATION_JSON)
	        .type(MediaType.APPLICATION_JSON)
	        .header("X-Auth-Token", ApiTokenIntegracao.TOKEN_GET_RESPONSE)
	        .post(ClientResponse.class, json);
	    
	    System.out.println(clientResponse.getEntity(String.class));
	    
	    clientResponse.close();
	}
	
	@Test
	public void testEnviaEmailporAPI() throws Exception {
		
	    NewsLetterGetResponse newsLetterGetResponse = new NewsLetterGetResponse();

	    newsLetterGetResponse.getSendSettings().getSelectedCampaigns().add("qKBgP"); /* Campanha e lista de e-mail para qual será enviado */
	    newsLetterGetResponse.setSubject("Email para teste de API");
	    newsLetterGetResponse.setName(newsLetterGetResponse.getSubject());

	    newsLetterGetResponse.getReplyTo().setFromFieldId("oEvv8"); /* ID email para resposta */
	    newsLetterGetResponse.getFromField().setFromFieldId("oEvv8"); /* ID do e-mail do remetente */
	    newsLetterGetResponse.getCampaign().setCampaignId("jSz0Y"); /* Campanha de origem, campanha pai */

	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate hoje = LocalDate.now();
	    LocalDate amanha = hoje.plusDays(1);
	    String dataEnvio = amanha.format(dateTimeFormatter);
	    
	    newsLetterGetResponse.setSendOn(dataEnvio + "T15:20:52-03:00");
	    
	    newsLetterGetResponse.getContent().setHtml("<html><h3>Email escrito com html</h3></html>");

	    
	    String json = new ObjectMapper().writeValueAsString(newsLetterGetResponse);

	    Client client = new HostIgnoringCliente(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringCliente();
	    WebResource webResource = client.resource(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE + "newsletters");

	    ClientResponse clientResponse = webResource
	        .accept(MediaType.APPLICATION_JSON)
	        .type(MediaType.APPLICATION_JSON)
	        .header("X-Auth-Token", ApiTokenIntegracao.TOKEN_GET_RESPONSE)
	        .post(ClientResponse.class, json);
	    
	    System.out.println(clientResponse.getEntity(String.class));
	    
	    clientResponse.close();
	}

	
	@Test
	public void testBuscaFromFieldId() throws Exception {
	    Client client = new HostIgnoringCliente(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringCliente();
	    WebResource webResource = client.resource(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE + "from-fields");

	    String clientResponse = webResource
	        .accept(MediaType.APPLICATION_JSON)
	        .type(MediaType.APPLICATION_JSON)
	        .header("X-Auth-Token", ApiTokenIntegracao.TOKEN_GET_RESPONSE)
	        .get(String.class);

	    System.out.println(clientResponse);
	}



}
