package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ObjetoFromFieldIdGetResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fromFieldId;
    private String email;
    private String name;
    private String isDefault;
    private String isActive;
    private String createdOn;
    private String href;
    
}

