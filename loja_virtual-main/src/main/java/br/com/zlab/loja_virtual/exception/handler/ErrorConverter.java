package br.com.zlab.loja_virtual.exception.handler;

import java.util.Map;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.zlab.loja_virtual.dto.ObjetoErroDTO;
import br.com.zlab.loja_virtual.exception.ErroDaApi;

@Component
public class ErrorConverter {

	public ResponseEntity<Object> criarJson(ErroDaApi erroDaApi, String msgDeErro, HttpStatus badRequest) {
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		objetoErroDTO.setError(msgDeErro);
		objetoErroDTO.setCode(erroDaApi.getCodigo());

		return new ResponseEntity<>(objetoErroDTO, badRequest);
	}

	public ResponseEntity<Object> criarJson(ErroDaApi erroDaApi, String msgDeErro) {
		return criarJson(erroDaApi, msgDeErro, HttpStatus.BAD_REQUEST);
	}

	// You can keep this method for other use cases
	public JSONObject criarJsonDeErro(ErroDaApi erroDaApi, String msgDeErro) {
		JSONObject body = new JSONObject();
		JSONObject detalhe = new JSONObject();
		detalhe.put("mensagem", msgDeErro);
		detalhe.put("codigo", erroDaApi.getCodigo());
		JSONArray detalhes = new JSONArray();
		detalhes.put(detalhe);
		body.put("erros", detalhes);
		return body;
	}

	public Map<String, Object> criarMapDeErro(ErroDaApi erroDaApi, String msgDeErro) {

		JSONObject body = new JSONObject();

		JSONObject detalhe = new JSONObject();
		detalhe.put("mensagem", msgDeErro);
		detalhe.put("codigo", erroDaApi.getCodigo());

		JSONArray detalhes = new JSONArray();
		detalhes.put(detalhe);

		body.put("erros", detalhes);

		return criarJsonDeErro(erroDaApi, msgDeErro).toMap();

	}

}
