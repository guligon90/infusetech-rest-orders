package com.infusetech.rest.orders.models.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderUpdateDTO {
    @Size(min = 5, max = 100)
    private String nome;

    private Long numeroControle;
    private Long codigoCliente;
    private LocalDate dataCadastro;
    private double valor = -1.0;
    private int quantidade = -1;

    @AssertTrue(message = "Quantidade deve ser um inteiro positivo")
    @JsonIgnore
    public boolean isQuantidadePositive() {
        return quantidade > 0;
    }

    @AssertTrue(message = "Valor deve ser positivo ou zero")
    @JsonIgnore
    public boolean isValorPositiveOrZero() {
        return valor >= 0;
    }
}
