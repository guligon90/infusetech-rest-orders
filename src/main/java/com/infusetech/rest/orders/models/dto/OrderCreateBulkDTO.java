package com.infusetech.rest.orders.models.dto;

import java.util.List;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderCreateBulkDTO implements Serializable {
    public static final int LISTA_PEDIDOS_MIN_SIZE = 1;
    public static final int LISTA_PEDIDOS_MAX_SIZE = 10;

    @XmlElementWrapper(name="pedidos")
    @XmlElement(name="pedido")
    private List<OrderCreateDTO> pedidos;

    @JsonIgnore
    public boolean isPedidosListSizeOutOfRange() {
        int size = pedidos.size();

        return !(size >= LISTA_PEDIDOS_MIN_SIZE && size <= LISTA_PEDIDOS_MAX_SIZE);
    }
}
