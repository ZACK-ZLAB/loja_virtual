package br.com.zlab.loja_virtual.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.zlab.loja_virtual.exception.dto.CampanhaGetResponse;
import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponse;
import br.com.zlab.loja_virtual.util.ApiTokenIntegracao;
import jakarta.ws.rs.core.MediaType;

@Service
public class ServiceGetResponseEmailMarketing {

    public List<CampanhaGetResponse> carregaListaCampanhaGetResponse() throws Exception {
    	
    	 Client client = new HostIgnoringCliente(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringCliente();

 	    String json = client.resource(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE + "campaigns")
 	            .accept(MediaType.APPLICATION_JSON)
 	            .type(MediaType.APPLICATION_JSON)
 	            .header("X-Auth-Token", ApiTokenIntegracao.TOKEN_GET_RESPONSE)
 	            .get(String.class);

 	    ObjectMapper objectMapper = new ObjectMapper();
 	    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

 	    List<CampanhaGetResponse> list = objectMapper.readValue(json, new TypeReference<List<CampanhaGetResponse>>() {});
 	    
        return list;
    }
    
    public String criaLeadApiGetResponse(LeadCampanhaGetResponse leadCampanhaGetResponse) throws Exception {
        String json = new ObjectMapper().writeValueAsString(leadCampanhaGetResponse);

        Client client = new HostIgnoringCliente(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringCliente();
        WebResource webResource = client.resource(ApiTokenIntegracao.URL_END_POINT_GET_RESPONSE + "contacts");

        ClientResponse clientResponse = webResource
            .accept(MediaType.APPLICATION_JSON)
            .type(MediaType.APPLICATION_JSON)
            .header("X-Auth-Token", ApiTokenIntegracao.TOKEN_GET_RESPONSE)
            .post(ClientResponse.class, json);

        String retorno = clientResponse.getEntity(String.class);
        
        
        if(clientResponse.getStatus() == 202) {
        	retorno = "Cadastrado com sucesso";
        }
        
        clientResponse.close();
        return retorno;
    }

}