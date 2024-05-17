package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.zlab.loja_virtual.exception.dto.LeadCampanhaGetResponseCadastrado;
import lombok.Data;

@Data
public class NewsLetterGetResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Conteúdo do e-mail em html ou texto */
    private Content content = new Content();

    private ArrayList<String> flags = new ArrayList<String>();

    /* Nome do email no máximo 128 letras */
    private String name;

    /* Tipo broadcast: transmissão ou draft rascunho */
    private String type = "broadcast";

    private String editor = "custom";

    /* Assunto do e-mail */
    private String subject;

    /* Email da pessoa que está enviando */
    private FromField fromField = new FromField();

    /* Email para endereço de resposta */
    private ReplyTo replyTo = new ReplyTo();

    /* Campanha na qual o e-mail é atribuído */
    private LeadCampanhaGetResponseCadastrado campaign = new LeadCampanhaGetResponseCadastrado();

    /* Data de envio 2022-05-12T18:20:52-03:00 */
    private String sendOn;

    /* Os anexos e arquivos caso queira enviar */
    private ArrayList<AttachmenteNewsLatterGetResponse> attachments = new ArrayList<AttachmenteNewsLatterGetResponse>();

    /* Configurações extras */
    private SendSettings sendSettings = new SendSettings();

}
