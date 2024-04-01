package br.com.mercadoon.enumeration;

public enum FuncaoCartao {
    CREDITO(0),
    DEBITO(1);

    final int valor;

    FuncaoCartao(int valor) {
        this.valor = valor;
    }
}
