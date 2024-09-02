package com.infusetech.rest.orders.models.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infusetech.rest.orders.models.OrderConstraints;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderCreateDTO {
    @NotBlank(message = OrderConstraints.Create.NOME_IS_BLANK_ERROR_MESSAGE)
    @Size(
        min = OrderConstraints.Common.NOME_MIN_SIZE,
        max = OrderConstraints.Common.NOME_MAX_SIZE,
        message = OrderConstraints.Common.NOME_OUT_OF_RANGE_ERROR_MESSAGE
    )
    private String nome;

    @NotNull(message = OrderConstraints.Create.NUMERO_CONTROLE_IS_NULL_ERROR_MESSAGE)
    private Long numeroControle;

    @NotNull(message = OrderConstraints.Create.CODIGO_CLIENTE_IS_NULL_ERROR_MESSAGE)
    private Long codigoCliente;

    @Positive(message = OrderConstraints.Create.VALOR_ERROR_MESSAGE)
    private double valor;

    private int quantidade = 1;
    private LocalDate dataCadastro = LocalDate.now();

    @AssertTrue(message = OrderConstraints.Create.QUANTIDADE_ERROR_MESSAGE)
    @JsonIgnore
    public boolean isQuantidade() {
        return quantidade > 0;
    }
}
