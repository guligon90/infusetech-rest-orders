package com.infusetech.rest.orders.models.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotBlank
    @Size(min = 10, max = 100)
    private String nome;

    @NotBlank
    private String codigoCliente;

    @NotBlank
    private Long numeroControle;
    
    @NotBlank
    private double valor;
    
    private LocalDate dataCadastro = LocalDate.now();

    private int quantidade = 1;    
}
