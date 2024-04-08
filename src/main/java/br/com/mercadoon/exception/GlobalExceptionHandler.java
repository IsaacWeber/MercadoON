package br.com.mercadoon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(NotFound e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseValidation> handle(MethodArgumentNotValidException e) {
        ErrorResponseValidation errorValidation = new ErrorResponseValidation();
        errorValidation.setStatus(HttpStatus.BAD_REQUEST.value());
        errorValidation.setTitulo("Algum campo não foi preenchido corretamente.");

        List<ErrorResponseValidation.Campo> campos = new ArrayList<>();

        // Adiciona para a lista de campos os campos com a correspondente mensagem de erro
        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> campos
                        .add(errorValidation
                                .new Campo(((FieldError) error).getField(),
                                            error.getDefaultMessage())));

        errorValidation.setCampos(campos);

        return new ResponseEntity<>(errorValidation, HttpStatus.BAD_REQUEST);
    }
}
