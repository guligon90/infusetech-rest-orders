package com.infusetech.rest.orders.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.infusetech.rest.orders.common.mappers.ModelDtoMapper;
import com.infusetech.rest.orders.models.Order;
import com.infusetech.rest.orders.models.dto.OrderCreateDTO;
import com.infusetech.rest.orders.models.dto.OrderDTO;
import com.infusetech.rest.orders.models.dto.OrderUpdateDTO;
import com.infusetech.rest.orders.repositories.OrderRepository;
import com.infusetech.rest.orders.services.exceptions.DataBindingViolationException;
import com.infusetech.rest.orders.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    private double applyDiscount(double value, int quantity) {
        // Desconto de 5%
        if (5 < quantity && quantity < 10) return value * 0.95;

        // Desconto de 10%
        if (quantity >= 10) return value * 0.9;

        // Fallback (sem desconto)
        return value;
    }

    public void delete(Long id) {
        findById(id);

        try {
            this.orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public OrderDTO findById(Long id) {
        Optional<Order> result = this.orderRepository.findById(id);

        Order order = result.orElseThrow(() -> new ObjectNotFoundException(
            "Pedido com ID: '" + id + "' não encontrado, Tipo: " + Order.class.getName()));

        return ModelDtoMapper.<Order, OrderDTO>getDTO(order, OrderDTO.class);
    }

    public Page<Order> findBySearchCriteria(Specification<Order> spec, Pageable page){
        Page<Order> searchResult = this.orderRepository.findAll(spec, page);

        return searchResult;
    }


    public OrderDTO create(OrderCreateDTO payload) {
        Order toBeCreated = ModelDtoMapper.<Order, OrderCreateDTO>getModel(payload, Order.class);
        double value = toBeCreated.getValor();
        int quantity = payload.getQuantidade();

        toBeCreated.setValor(applyDiscount(value, quantity));

        Order created = orderRepository.saveAndFlush(toBeCreated);

        return ModelDtoMapper.<Order, OrderDTO>getDTO(created, OrderDTO.class);
    }

    public OrderDTO update(Long id, OrderUpdateDTO payload) {
        Order fetchedOrder = ModelDtoMapper.<Order, OrderDTO>getModel(findById(id), Order.class);

        if (payload.getQuantidade() == -1.0) {
            payload.setQuantidade(fetchedOrder.getQuantidade());
        }

        if (payload.getValor() == -1) {
            payload.setValor(fetchedOrder.getValor());
        }

        // Aplica desconto somente se houver alteração na quantidade ou no valor
        boolean toBeDiscounted = (payload.getQuantidade() != fetchedOrder.getQuantidade())
            || (payload.getValor() != fetchedOrder.getValor());

        if (toBeDiscounted)
            payload.setValor(applyDiscount(payload.getValor(), payload.getQuantidade()));

        Order toBeUpdated = ModelDtoMapper.<Order, OrderUpdateDTO>updateModelWithDTOData(fetchedOrder, payload, Order.class);
        Order updated = orderRepository.saveAndFlush(toBeUpdated);

        return ModelDtoMapper.<Order, OrderDTO>getDTO(updated, OrderDTO.class);
    }
}
