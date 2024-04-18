package br.com.mercadoon.entity;

import br.com.mercadoon.enumeration.CategoriaProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "compra_produto",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "compra_id"))
    private List<Compra> compras;
}
