package com.infusetech.rest.orders.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = Order.TABLE_NAME)
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDate dataCadastro;

    @Column(name = "valor", precision = 2)
    private double valor;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigoCliente() {
        return this.codigoCliente;
    }

    public void setCodigoCliente(Long codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Long getNumeroControle() {
        return this.numeroControle;
    }

    public void setNumeroControle(Long numeroControle) {
        this.numeroControle = numeroControle;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
