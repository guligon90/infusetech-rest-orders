package com.infusetech.rest.orders.models.dto;

import java.util.List;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infusetech.rest.orders.models.OrderConstraints;

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
    @XmlElementWrapper(name="pedidos")
    @XmlElement(name="pedido")
    private List<OrderCreateDTO> pedidos;

    @JsonIgnore
    public boolean isPedidosListSizeOutOfRange() {
        int size = pedidos.isEmpty() ? 0 : pedidos.size();

        return !(size >= OrderConstraints.Create.LISTA_PEDIDOS_MIN_SIZE &&
            size <= OrderConstraints.Create.LISTA_PEDIDOS_MAX_SIZE);
    }
}
