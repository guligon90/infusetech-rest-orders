package com.infusetech.rest.orders.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infusetech.rest.orders.common.api.ApiResponse;
import com.infusetech.rest.orders.common.api.ResponseBuilder;
import com.infusetech.rest.orders.common.filters.SearchCriteria;
import com.infusetech.rest.orders.filtering.orders.dto.OrderSearchDTO;
import com.infusetech.rest.orders.filtering.orders.specification.OrderSpecificationBuilder;
import com.infusetech.rest.orders.models.Order;
import com.infusetech.rest.orders.services.OrderService;

@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> findById(@PathVariable Long id) {
        Order result = this.orderService.findById(id);

        ResponseBuilder<Order> rb = new ResponseBuilder<Order>();

        return rb.buildResponse(
            HttpStatus.OK.value(),
            "Detalhes do pedido",
            result
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        this.orderService.delete(id);

        ResponseBuilder<Object> rb = new ResponseBuilder<Object>();

        return rb.buildResponse(
            HttpStatus.NO_CONTENT.value(),
            "Pedido removido com sucesso"
        );
    }

    @PostMapping(path = "/search")
    public ResponseEntity<ApiResponse<List<Order>>> searchOrders(
        @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
        @RequestBody OrderSearchDTO orderSearchDTO
    ) {
        OrderSpecificationBuilder builder = new OrderSpecificationBuilder();
        List<SearchCriteria> criteriaList = orderSearchDTO.getSearchCriteriaList();

        if(criteriaList != null){
            criteriaList.forEach(x-> {
                x.setDataOption(orderSearchDTO.getDataOption());
                builder.with(x);
        });

        }

        Pageable page = PageRequest.of(
            pageNum,
            pageSize,
            Sort.by("id")
                .ascending()
                .and(Sort.by("numeroControle"))
                .ascending()
                .and(Sort.by("codigoCliente"))
                .ascending()
        );

        Page<Order> orderPage = orderService.findBySearchCriteria(builder.build(), page);

        ResponseBuilder<List<Order>> rb = new ResponseBuilder<List<Order>>();

        return rb.buildResponse(
            HttpStatus.OK.value(),
            "Resultados da busca por pedidos",
            orderPage.toList()
        );
    }
}
