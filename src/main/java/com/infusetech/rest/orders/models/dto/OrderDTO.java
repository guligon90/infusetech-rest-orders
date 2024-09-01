package com.infusetech.rest.orders.models.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String nome;
    private String codigoCliente;
    private LocalDate dataCadastro;    
    private int quantidade;
    private double valor;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Long numeroControle;
}
