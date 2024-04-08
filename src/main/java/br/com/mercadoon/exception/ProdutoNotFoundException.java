package br.com.mercadoon.exception;

public class ProdutoNotFoundException extends NotFound {
    public ProdutoNotFoundException(String msg) {
        super(msg);
    }
}
