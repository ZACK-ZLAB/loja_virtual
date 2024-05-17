package br.com.zlab.loja_virtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.zlab.loja_virtual.exception.dto.CampanhaGetResponse;
import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponse;
import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponseCadastrado;
import br.com.zlab.loja_virtual.service.HostIgnoringCliente;
import br.com.zlab.loja_virtual.util.ApiTokenIntegracao;
import jakarta.ws.rs.core.MediaType;
import junit.framework.TestCase;

@Profile("devp")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteCampanhaGetResponse extends TestCase {
	
	@Test
	public void testCarregaCampanhaGetResponse() throws Exception {

	    Client client = new HostIgnoringCliente(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringCliente();

	    String json = client.resource(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE + "campaigns")
	            .accept(MediaType.APPLICATION_JSON)
	            .type(MediaType.APPLICATION_JSON)
	            .header("X-Auth-Token", ApiTokenIntegracao.TOKEN_GET_RESPONSE)
	            .get(String.class);

	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

	    List<CampanhaGetResponse> list = objectMapper.readValue(json, new TypeReference<List<CampanhaGetResponse>>() {});

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


}
