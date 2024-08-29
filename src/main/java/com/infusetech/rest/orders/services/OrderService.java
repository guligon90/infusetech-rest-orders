package com.infusetech.rest.orders.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.infusetech.rest.orders.mappers.ModelDtoMapper;
import com.infusetech.rest.orders.models.Order;
import com.infusetech.rest.orders.models.dto.OrderDTO;
import com.infusetech.rest.orders.repositories.OrderRepository;
import com.infusetech.rest.orders.services.exceptions.DataBindingViolationException;
import com.infusetech.rest.orders.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    public OrderDTO create(OrderDTO employeeDto){
        Order emp = ModelDtoMapper.getOrder(employeeDto);

        return ModelDtoMapper.getOrderDTO(orderRepository.saveAndFlush(emp));
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

    public Order findById(Long id) {
        Optional<Order> result = this.orderRepository.findById(id);

        return result.orElseThrow(() -> new ObjectNotFoundException(
            "Pedido com ID: '" + id + "' não encontrado, Tipo: " + Order.class.getName()));
    }

    public Page<Order> findBySearchCriteria(Specification<Order> spec, Pageable page){
        Page<Order> searchResult = this.orderRepository.findAll(spec, page);

        return searchResult;
    }
}
