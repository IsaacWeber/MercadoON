package br.com.mercadoon.exception;

public class NotFound extends RuntimeException {
    public NotFound(String msg) {
        super(msg);
    }
}
