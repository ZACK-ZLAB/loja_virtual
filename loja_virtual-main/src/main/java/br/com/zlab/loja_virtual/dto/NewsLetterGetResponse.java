package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponseCadastrado;
import lombok.Data;

@Data
public class NewsLetterGetResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Content contentObject;
    private ArrayList<Object> flags = new ArrayList<>();
    private String name;
    private String type;
    private String editor;
    private String subject;
    private FromField fromField;
    private ReplyTo replyTo;
    private LeadCampanhaGetResponseCadastrado campaign;
    private String sendOn;
    private ArrayList<AttachmenteNewsLatterGetResponse> attachments = new ArrayList<>();
    private SendSettings sendSettings;
}
