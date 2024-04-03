package br.com.mercadoon.enumeration;

public enum FuncaoCartao {
    DEBITO(0),
    CREDITO(1);

    final int valor;

    FuncaoCartao(int valor) {
        this.valor = valor;
    }
}
