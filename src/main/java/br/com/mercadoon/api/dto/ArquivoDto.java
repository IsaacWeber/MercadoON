package br.com.mercadoon.api.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArquivoDto {
    private Long id;
    private String nome;
    private String tipo;
    private byte[] data;
}
