package br.com.mercadoon.dto;

import br.com.mercadoon.enumeration.BandeiraCartao;
import br.com.mercadoon.enumeration.FuncaoCartao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoDto {
    private String nomeUsuario;
    private String numero;
    private BandeiraCartao bandeira;
    private FuncaoCartao funcao;

}
