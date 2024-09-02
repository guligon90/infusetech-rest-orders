package com.infusetech.rest.orders.models.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infusetech.rest.orders.models.OrderConstraints;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderUpdateDTO {
    @Size(
        min = OrderConstraints.Common.NOME_MIN_SIZE,
        max = OrderConstraints.Common.NOME_MAX_SIZE,
        message = OrderConstraints.Common.NOME_OUT_OF_RANGE_ERROR_MESSAGE
    )
    private String nome;

    private Long numeroControle;
    private Long codigoCliente;
    private LocalDate dataCadastro;
    private double valor = 0;
    private int quantidade = 0;

    @AssertTrue(message = OrderConstraints.Update.QUANTIDADE_ERROR_MESSAGE)
    @JsonIgnore
    public boolean isQuantidadePositiveOrZero() {
        return quantidade >= 0;
    }

    @AssertTrue(message = OrderConstraints.Update.VALOR_ERROR_MESSAGE)
    @JsonIgnore
    public boolean isValorPositiveOrZero() {
        return valor >= 0;
    }
}
