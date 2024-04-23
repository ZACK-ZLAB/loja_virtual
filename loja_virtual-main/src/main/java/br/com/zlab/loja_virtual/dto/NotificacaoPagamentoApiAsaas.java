package br.com.zlab.loja_virtual.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NotificacaoPagamentoApiAsaas {

    private String event;
    private Payment payment = new Payment();
    
    
    public String idFatura() {
        return payment.getId();
    }

    public String statusPagamento() {
        return getPayment().getStatus();
    }

    public Boolean boletoPixFaturaPaga() {
        return statusPagamento().equalsIgnoreCase("CONFIRMED") || statusPagamento().equalsIgnoreCase("RECEIVED");
    }

}
