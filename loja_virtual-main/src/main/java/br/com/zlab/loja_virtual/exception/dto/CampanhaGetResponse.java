package br.com.zlab.loja_virtual.exception.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CampanhaGetResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String description;
    private String campaignId;
    private String name;
    private String techName;
    private String languageCode;
    private String isDefault;
    private String createdOn;
    private String href;
    
    
}
