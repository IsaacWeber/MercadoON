package br.com.mercadoon.exception;

public class CartaoNotFoundException extends RuntimeException {
    public CartaoNotFoundException(String msg) {
        super(msg);
    }
}
