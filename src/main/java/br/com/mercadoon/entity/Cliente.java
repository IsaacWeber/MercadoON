package br.com.mercadoon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@Entity
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="sobrenome")
    private String sobrenome;

    @Column(name="cpf")
    private String cpf;

    @Column(name="email")
    private String email;

    @Column(name="endereco")
    private String endereco;

    @Column(name="membro_desde")
    private Date membroDesde;

}
