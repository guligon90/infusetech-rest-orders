package com.infusetech.rest.orders.models.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderCreateDTO {
    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    @NotNull
    private Long numeroControle;
    
    @NotNull
    private Long codigoCliente;    
    
    @Positive
    private double valor;

    private int quantidade = 1;    
    private LocalDate dataCadastro = LocalDate.now();

    @AssertTrue(message = "Quantidade deve ser um inteiro positivo")
    @JsonIgnore
    public boolean isQuantidadePositive() {
        return quantidade > 0;
    }
}
