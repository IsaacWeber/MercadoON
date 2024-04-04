package br.com.mercadoon.enumeration;

public enum CategoriaProduto {
    ELETRONICOS(0),
    ELETRODOMESTICOS(1),
    VEICULOS(2),
    ALIMENTOS(3),
    AGRO(4),
    ACESSORIOS(5),
    ROUPAS(6),
    CALCADOS(7),
    IMOVEIS(8),
    ESPORTES(9),
    FERRAMENTAS(10),
    INFORMATICA(11),
    SERVICOS(12),
    OUTROS(13);

    final int valor;

    CategoriaProduto(int valor) {
        this.valor = valor;
    }


}
