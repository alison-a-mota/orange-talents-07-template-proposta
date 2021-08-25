package br.com.zup.proposta.compartilhado.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> interceptaBeanValidation(MethodArgumentNotValidException exception) {
        var fieldErrors = exception.getBindingResult().getFieldErrors();
        var campos = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        var mensagens = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        var response = new ExceptionResponse(campos, mensagens, String.valueOf(HttpStatus.BAD_REQUEST));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> interceptaBeanValidation(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Corpo da requisição inválido.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> interceptaBeanValidation(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> interceptaBeanValidation(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> interceptaQuedaConexoesExternas(ConnectException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não houve resposta do client.");
    }
}