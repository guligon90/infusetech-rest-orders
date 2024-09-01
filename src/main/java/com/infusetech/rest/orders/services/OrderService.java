package com.infusetech.rest.orders.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.infusetech.rest.orders.common.mappers.ModelDtoMapper;
import com.infusetech.rest.orders.models.Order;
import com.infusetech.rest.orders.models.dto.OrderCreateBulkDTO;
import com.infusetech.rest.orders.models.dto.OrderCreateDTO;
import com.infusetech.rest.orders.models.dto.OrderDTO;
import com.infusetech.rest.orders.models.dto.OrderUpdateDTO;
import com.infusetech.rest.orders.repositories.OrderRepository;
import com.infusetech.rest.orders.services.exceptions.DataBindingViolationException;
import com.infusetech.rest.orders.services.exceptions.ObjectNotFoundException;

import jakarta.validation.ValidationException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    private static double applyDiscount(double value, int quantity) {
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

    public OrderDTO findById(Long id) {
        Optional<Order> result = this.orderRepository.findById(id);

        Order order = result.orElseThrow(() -> new ObjectNotFoundException(
            "Pedido com ID: '" + id + "' não encontrado, Tipo: " + Order.class.getName()));

        return ModelDtoMapper.<Order, OrderDTO>getDTO(order, OrderDTO.class);
    }

    public Page<OrderDTO> findBySearchCriteria(Specification<Order> specification, Pageable pageRequest){
        List<OrderDTO> results = this.orderRepository.findAll(specification)
            .stream()
            .map(item -> ModelDtoMapper
                .<Order, OrderDTO>getDTO(
                    item,
                    OrderDTO.class
                )
            ).toList();

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), results.size());

        List<OrderDTO> pageContent = results.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, results.size());
    }

    public OrderDTO create(OrderCreateDTO payload) {
        Order toBeCreated = ModelDtoMapper.<Order, OrderCreateDTO>getModel(payload, Order.class);
        double value = toBeCreated.getValor();
        int quantity = payload.getQuantidade();

        if (value > 0)
            toBeCreated.setValor(OrderService.applyDiscount(value, quantity));

        return ModelDtoMapper
            .<Order, OrderDTO>getDTO(
                orderRepository.saveAndFlush(toBeCreated),
                OrderDTO.class
            );
    }

    public OrderDTO update(Long id, OrderUpdateDTO payload) {
        Order fetchedOrder = ModelDtoMapper.<Order, OrderDTO>getModel(findById(id), Order.class);

        if (payload.getQuantidade() == 0) {
            payload.setQuantidade(fetchedOrder.getQuantidade());
        }

        if (payload.getValor() == 0) {
            payload.setValor(fetchedOrder.getValor());
        }

        Double value = payload.getValor();
        int quantity = payload.getQuantidade();

        // Aplica desconto somente...
        boolean toBeDiscounted = (
            (quantity != fetchedOrder.getQuantidade()) || // ...se houver alteração na quantidade
            (value != fetchedOrder.getValor()) // ...ou no valor
        ) && value > 0; // ...e se o valor for positivo

        if (toBeDiscounted) {
            Double discount = OrderService.applyDiscount(value, quantity);
            payload.setValor(discount);
        }

        Order toBeUpdated = ModelDtoMapper
            .<Order, OrderUpdateDTO>updateModelWithDTOData(
                fetchedOrder, payload,
                Order.class
            );

        return ModelDtoMapper
            .<Order, OrderDTO>getDTO(
                orderRepository.saveAndFlush(toBeUpdated),
                OrderDTO.class
            );
    }

    public List<OrderDTO> createOrdersInBulk(OrderCreateBulkDTO payload) {
        List<OrderCreateDTO> orders = payload.getPedidos();

        if (payload.isPedidosListSizeOutOfRange()) {
            throw new ValidationException("A lista de pedidos deve possuir entre " +
                OrderCreateBulkDTO.LISTA_PEDIDOS_MIN_SIZE + " e " +
                OrderCreateBulkDTO.LISTA_PEDIDOS_MAX_SIZE + " elementos"
            );
        }

        // TODO; introduzir validação e concorrência para muitos registros
        return orders.stream().map(order -> create(order)).toList();
    }
}
