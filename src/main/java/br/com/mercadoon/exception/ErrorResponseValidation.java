package br.com.mercadoon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ErrorResponseValidation {
    private int status;
    private String titulo;
    private List<Campo> campos;

    @Getter
    @Setter
    @AllArgsConstructor
    public class Campo {
        private String nome;
        private String mensagem;
    }

}
