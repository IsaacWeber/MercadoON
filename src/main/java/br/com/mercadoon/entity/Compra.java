package br.com.mercadoon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "compra_produto",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Column(name = "realizacao")
    private Date realizacao;

    @Column(name = "previsao_entrega")
    private Date previsaoEntrega;

    @Column(name = "entrega")
    private Date entrega;

    //TODO colocar coluna de status com enumeracao(ENTREGUE, EM PROCESSO, INTERROMPIDO, ...) (pensar nas possibilidades para a enumeração)
}
