package br.com.mercadoon.requestmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public class CompraRequestModel {

    @NotNull(message = "clienteId não pode ser nulo ou vazio.")
    private Long clienteId;

    @NotNull(message = "cartaoId não pode ser nulo ou vazio.")
    private Long cartaoId;

    @JsonProperty
    @Size(min = 1, message = "produtosId deve conter pelo menos um id do respectivo produto.")
    @NotNull(message = "produtosId não pode ser nulo.")
    private List<Long> produtosId; //TODO criar anotação customizada para avaliar. Não pode haver elementos vazios ou nulos

}
