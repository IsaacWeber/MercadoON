package br.com.mercadoon.entity;

import br.com.mercadoon.enumeration.CategoriaProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    @NotEmpty(message = "Nome não pode estar vazio ou nulo.")
    private String nome;

    @Column(name = "marca")
    @NotEmpty(message = "Marca não pode estar vazio ou nulo.")
    private String marca;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "categoria")
    @NotNull(message = "Categoria não pode estar nulo.")
    private CategoriaProduto categoria;

    @Column(name = "modelo")
    @NotEmpty(message = "Modelo não pode estar vazio ou nulo.")
    private String modelo;

    @Column(name = "cor")
    @NotEmpty(message = "Cor não pode estar vazio ou nulo.")
    private String cor;

    @Column(name = "descricao")
    @NotEmpty(message = "Descrição não pode estar vazio ou nulo.")
    private String descricao;

    @Column(name = "descricao_tecnica")
    private String descricaoTecnica;

    @Column(name = "preco")
    @NotNull(message = "O preço não pode ser nulo")
    private Double preco;
}
