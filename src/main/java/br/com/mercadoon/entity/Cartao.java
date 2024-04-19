package br.com.mercadoon.entity;

import br.com.mercadoon.enumeration.BandeiraCartao;
import br.com.mercadoon.enumeration.FuncaoCartao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="cartao")
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_usuario")
    @NotEmpty(message = "O nome do usuário não pode ser vazio ou nulo.")
    private String nomeUsuario;

    @Column(name = "numero")
    @NotEmpty(message = "O número do cartão não pode ser vazio ou nulo.")
    private String numero;

    @Column(name = "cvv")
    @NotEmpty(message = "O cvv não pode ser vazio ou nulo.")
    @Size(min = 3, max = 4, message = "cvv deve ter 3 ou 4 dígitos")
    private String cvv;

    @Column(name = "validade")
    @Pattern(regexp = "\\d{1,2}/\\d{2}", message = "A validade deve estar no formato mm/aa")
    private String validade;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "bandeira")
    @NotNull(message = "Bandeira não pode ser nulo") // TODO criar anotação específica para validar (Não pode estar vazia)
    private BandeiraCartao bandeira;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "funcao")
    @NotNull(message = "Função não pode ser nulo") // TODO criar anotação específica para validar (Não pode estar vazia)
    private FuncaoCartao funcao;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Compra> compras;
}
