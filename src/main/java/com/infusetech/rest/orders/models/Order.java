package com.infusetech.rest.orders.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Order.TABLE_NAME)
public class Order {
    public static final String TABLE_NAME = "pedido";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "codigo_cliente")
    private Long codigoCliente;

    @Column(name = "numero_controle", unique = true)
    private Long numeroControle;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_cadastro", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCadastro;

    @Column(name = "valor", precision = 2)
    private double valor;
}
