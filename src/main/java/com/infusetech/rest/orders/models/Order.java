package com.infusetech.rest.orders.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order {
    public interface CreateOrder {}
    public interface UpdateOrder {}

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
    @NotNull(groups = { CreateOrder.class, UpdateOrder.class })
    @NotEmpty(groups = { CreateOrder.class, UpdateOrder.class })
    private String nome;

    @Column(name = "data_cadastro", length = 100, nullable = false)
    @NotNull(groups = { CreateOrder.class, UpdateOrder.class })
    @NotEmpty(groups = { CreateOrder.class, UpdateOrder.class })
    private Date dataCadastro;

    public Order() {
    }

    public Order(Long id, Long codigoCliente, Long numeroControle, int quantidade, String nome, Date dataCadastro) {
        this.id = id;
        this.codigoCliente = codigoCliente;
        this.numeroControle = numeroControle;
        this.quantidade = quantidade;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
    }   


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

    public Date getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
