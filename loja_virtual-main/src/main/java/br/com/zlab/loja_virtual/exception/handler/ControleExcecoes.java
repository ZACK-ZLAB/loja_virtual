package br.com.zlab.loja_virtual.exception.handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import br.com.zlab.loja_virtual.exception.BusinessException;
import br.com.zlab.loja_virtual.exception.ConverterException;
import br.com.zlab.loja_virtual.exception.ErroDaApi;
import br.com.zlab.loja_virtual.exception.IntegracaoException;
import br.com.zlab.loja_virtual.exception.RegistroNaoEncontradoException;
import br.com.zlab.loja_virtual.exception.dto.ObjetoErroDTO;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes {

    @Autowired
    private ErrorConverter errorConverter;

    @ExceptionHandler(LojaVirtualException.class)
    public ResponseEntity<Object> handleExceptionCustom(LojaVirtualException ex) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        objetoErroDTO.setError(ex.getMessage());
        objetoErroDTO.setCode(HttpStatus.OK.toString());
        return new ResponseEntity<>(objetoErroDTO, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ObjetoErroDTO> handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        String errorMessage = "";

        for (ObjectError objectError : errors) {
            errorMessage += objectError.getDefaultMessage() + "\n";
        }

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        objetoErroDTO.setError(errorMessage);
        objetoErroDTO.setCode(HttpStatus.BAD_REQUEST.toString()); // Customize as needed

        return new ResponseEntity<>(objetoErroDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        String errorMessage = "";
        if (ex instanceof MethodArgumentNotValidException) {
            errorMessage = buildErrorMessage((MethodArgumentNotValidException) ex);
        } else if (ex instanceof HttpMessageNotReadableException) {
            errorMessage = "Não está sendo enviado dados para o BODY corpo da requisição";
        } else {
            errorMessage = ex.getMessage();
        }

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        objetoErroDTO.setError(errorMessage);
        objetoErroDTO.setCode(status.value() + " ==> " + status.getReasonPhrase());

        ex.printStackTrace();

        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String buildErrorMessage(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        StringBuilder errorMessage = new StringBuilder();

        for (ObjectError objectError : errors) {
            errorMessage.append(objectError.getDefaultMessage()).append("<br>");
        }

        return errorMessage.toString();
    }

    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorConverter.criarJson(ErroDaApi.BODY_INVALIDO,
                "O corpo (body) da requisição possui erros ou não existe", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException bde) {
        return errorConverter.criarJson(ErroDaApi.CREDENCIAIS_INVALIDAS,
                "Login e/ou senha inválidos");
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<Object> handleCredentialsExpired(CredentialsExpiredException eje) {
        return errorConverter.criarJson(ErroDaApi.TOKEN_EXPIRADO,
                "Token fora da validade (expirado)");
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<Object> handleInvalidDefinition(InvalidDefinitionException ide) {
        String atributo = ide.getPath().get(ide.getPath().size() - 1).getFieldName();
        String msgDeErro = "O atributo '" + atributo + "' possui formato inválido";
        return errorConverter.criarJson(ErroDaApi.FORMATO_INVALIDO, msgDeErro);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolation(ConstraintViolationException cve) {
        JSONObject body = new JSONObject();
        JSONArray msgs = new JSONArray();
        body.put("erros", msgs);
        cve.getConstraintViolations().forEach((error) -> {
            String[] paths = error.getPropertyPath().toString().split("\\.");
            String atributo = paths[paths.length - 1];
            String errorMessage = error.getMessage();
            String mensagemCompleta = "\"O atributo '" + atributo +
                    "' apresentou o seguinte erro: '" + errorMessage + "'\"";
            String plainJsonError = "{ codigo:" + ErroDaApi.CONDICAO_VIOLADA.getCodigo() +
                    ", mensagem: " + mensagemCompleta + " }";
            msgs.put(new JSONObject(plainJsonError));
        });
        return body.toMap();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ie) {
        return errorConverter.criarJson(ErroDaApi.PARAMETRO_INVALIDO, ie.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException npe) {
        return errorConverter.criarJson(ErroDaApi.PARAMETRO_INVALIDO, npe.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException be) {
        return errorConverter.criarJson(ErroDaApi.REGRA_VIOLADA, be.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException matme) {
        return errorConverter.criarJson(ErroDaApi.TIPO_PARAMETRO_INVALIDO, matme.getMessage());
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<Object> handleRegistroNaoEncontrado(RegistroNaoEncontradoException rnee) {
        return errorConverter.criarJson(ErroDaApi.REGISTRO_NAO_ENCONTRADO, rnee.getMessage());
    }

    @ExceptionHandler(ConverterException.class)
    public ResponseEntity<Object> handleConverterException(ConverterException ce) {
        return errorConverter.criarJson(ErroDaApi.CONVERSAO_INVALIDA, ce.getMessage());
    }

    @ExceptionHandler(IntegracaoException.class)
    public ResponseEntity<Object> handleIntegracaoException(IntegracaoException ie) {
        return errorConverter.criarJson(ErroDaApi.INTEGRACAO_INVALIDA, ie.getMessage());
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<Object> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ie) {
        return errorConverter.criarJson(ErroDaApi.INTEGRACAO_INVALIDA,
                "Ocorreu um erro de integração com a Api externa");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handlePSQLExceptions(DataIntegrityViolationException dve) {
        return errorConverter.criarJson(ErroDaApi.PARAMETRO_INVALIDO,
                "Ocorreu um erro de integridade referencial na base de dados");
    }

    // Add more handlers for other exceptions...

    // Common method for handling and sending email

    private String determineErrorMessage(Exception ex) {
        String msg = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            msg = list.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
        } else if (ex instanceof HttpMessageNotReadableException) {
            msg = "Não está sendo enviado dados para o BODY corpo da requisição";
        }
        return msg;
    }
}
