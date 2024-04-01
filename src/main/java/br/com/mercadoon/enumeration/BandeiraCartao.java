package br.com.mercadoon.enumeration;

public enum BandeiraCartao {
    MASTERCARD(0),
    VISA(1),
    ELO(2),
    ALELO(3);

    final int valor;

    BandeiraCartao(int valor) {
        this.valor = valor;
    }

}
