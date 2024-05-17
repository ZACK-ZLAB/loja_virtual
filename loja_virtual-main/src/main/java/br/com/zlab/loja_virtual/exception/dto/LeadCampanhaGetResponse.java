package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LeadCampanhaGetResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
    private String email;
    private String dayOfCycle = "0";
    private String scoring;
    //private String ipAddress;

    private LeadCampanhaGetResponseCadastrado campaign = new LeadCampanhaGetResponseCadastrado();
    private List<String> tags = new ArrayList<>();
    private List<CustomFieldValuesGetResponse> customFieldValues = new ArrayList<CustomFieldValuesGetResponse>();
    
}
