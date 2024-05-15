package br.com.zlab.loja_virtual.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.zlab.loja_virtual.exception.dto.WebManiaNotaFiscalEletronica;

@Service
public class WebManiaNotaFiscalService {

    public String emitirNotaFiscal(WebManiaNotaFiscalEletronica webManiaNotaFiscalEletronica) throws Exception {
        Client client = new HostIgnoringCliente("https://webmaniabr.com/api/").hostIgnoringCliente(); 
        WebResource webResource = client.resource("https://webmaniabr.com/api/1/nfe/emissao/");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(webManiaNotaFiscalEletronica);
        ClientResponse clientResponse = webResource
                                            .accept("application/json; charset=UTF-8")
                                            .header("X-Consumer-Key", "N6scITMtiEunElnEUAVMQpRAhJrHgU0e")
                                            .header("Content-Type", "application/json; charset=UTF-8")
                                            .header("X-Consumer-Secret", "6fBypsFYfQCTt6LBAkRjuGm6nHimRWoNCOiQ9aqLkcNEtHLR")
                                            .header("X-Access-Token", "3554-LLWm2CoGsTzxXW0QiLPmG2ha2f41S06hA3USo2IprD6J9Wwy")
                                            .header("X-Access-Token-Secret", "6RGm39tdeCYEgIvOKLuSdrGbiuK769Lx0Ngy3iueCVXCDPOE")
                                            .post(ClientResponse.class, json);
        String stringRetorno = clientResponse.getEntity(String.class);
        return stringRetorno;
    }
    
    public String cancelarNotaFiscal(String uuid, String motivo) throws Exception {
        Client client = new HostIgnoringCliente("https://webmaniabr.com/api/").hostIgnoringCliente(); 
        WebResource webResource = client.resource("https://webmaniabr.com/api/1/nfe/cancelar/");
        
        String json = "{\"uuid\":\"" + uuid + "\",\"motivo\":\"" + motivo + "\"}";
        
        ClientResponse clientResponse = webResource
                                            .accept("application/json; charset=UTF-8")
                                            .header("X-Consumer-Key", "N6scITMtiEunElnEUAVMQpRAhJrHgU0e")
                                            .header("Content-Type", "application/json; charset=UTF-8")
                                            .header("X-Consumer-Secret", "6fBypsFYfQCTt6LBAkRjuGm6nHimRWoNCOiQ9aqLkcNEtHLR")
                                            .header("X-Access-Token", "3554-LLWm2CoGsTzxXW0QiLPmG2ha2f41S06hA3USo2IprD6J9Wwy")
                                            .header("X-Access-Token-Secret", "6RGm39tdeCYEgIvOKLuSdrGbiuK769Lx0Ngy3iueCVXCDPOE")
                                            .put(ClientResponse.class, json);
        String stringRetorno = clientResponse.getEntity(String.class);
        return stringRetorno;
    }

}

