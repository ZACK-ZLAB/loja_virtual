package br.com.zlab.loja_virtual.dto;

/**
 * Armazena URL da API,  constantes de status,  chaves
 * @author zack_
 */
public class AsaasApiPagamentos {
	
	public static String BOLETO = "BOLETO";
	public static String CREDIT_CARD = "CREDIT_CARD";
	public static String PIX = "PIX";
	public static String BOLETO_PIX = "UNDEFINED"; /*cobrança não pode ser realizada por pix,  boleto e cartão*/
	
	public static String URL_API_ASAAS = "https://www.asaas.com/api/v3/";
	public static String URL_API_ASAAS_SANDBOX = "https://sandbox.asaas.com/api/v3";
	public static String API_KEY = "$aact_YTU5YTE0M2M2N2I4MTliNzk0YTI5N2U5MzdjNWZmNDQ6OjAwMDAwMDAwMDAwMDA0MDQyODI6OiRhYWNoX2Q0NTllNDQ0LWE1MjYtNDM0MC1iZTRjLWU3NzM2NDAxZjYxMw==";

}
