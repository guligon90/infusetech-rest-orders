package com.infusetech.rest.orders.controllers;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infusetech.rest.orders.common.api.ApiResponse;
import com.infusetech.rest.orders.common.api.ResponseBuilder;
import com.infusetech.rest.orders.common.filters.SearchCriteria;
import com.infusetech.rest.orders.filtering.orders.dto.OrderSearchDTO;
import com.infusetech.rest.orders.filtering.orders.dto.OrderSearchSortingDTO;
import com.infusetech.rest.orders.filtering.orders.specification.OrderSpecificationBuilder;
import com.infusetech.rest.orders.models.Order;
import com.infusetech.rest.orders.models.dto.OrderCreateDTO;
import com.infusetech.rest.orders.models.dto.OrderDTO;
import com.infusetech.rest.orders.models.dto.OrderUpdateDTO;
import com.infusetech.rest.orders.services.OrderService;

@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private static <T> ResponseBuilder<T> getResponseBuilder() {
        return new ResponseBuilder<T>();
    }

    private static Sort buildSortingCriteria(List<OrderSearchSortingDTO> sortingCriteriaList) {
        Sort sortingCriteria = Sort.by(sortingCriteriaList.get(0).getFilterKey());

        if (sortingCriteriaList.get(0).getAscending())
            sortingCriteria.ascending();
        else
            sortingCriteria.descending();

        for (int i = 1; i < sortingCriteriaList.size(); i++) {
            OrderSearchSortingDTO criteria = sortingCriteriaList.get(i);

            sortingCriteria.and(Sort.by(criteria.getFilterKey()));

            if (criteria.getAscending())
                sortingCriteria.descending();
            else
                sortingCriteria.descending();
        }

        return sortingCriteria;
    }

    private Pageable buildPaginatedResponse(
        OrderSearchDTO orderSearchDTO,
        OrderSpecificationBuilder builder,
        int pageNum,
        int pageSize
    ) {
        List<OrderSearchSortingDTO> sortingCriteriaList = orderSearchDTO.getSearchSortingCriteriaList();
        Sort defaultSorter = Sort.by("id").ascending();

        Sort sorter = sortingCriteriaList != null
            ? (
                sortingCriteriaList.size() > 0
                    ? OrderController.buildSortingCriteria(sortingCriteriaList)
                    : defaultSorter
            )
            : defaultSorter;

        Pageable page = PageRequest.of(pageNum, pageSize, sorter);

        return page;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> findById(@PathVariable Long id) {
        OrderDTO result = this.orderService.findById(id);

        return OrderController
            .<OrderDTO>getResponseBuilder()
            .buildResponse(
                HttpStatus.OK.value(),
                "Detalhes do pedido",
                result
            );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        this.orderService.delete(id);

        return OrderController
            .<Object>getResponseBuilder()
            .buildResponse(
                HttpStatus.NO_CONTENT.value(),
                "Pedido removido com sucesso"
            );
    }

    @PostMapping(path = "/search")
    public ResponseEntity<ApiResponse<List<Order>>> searchOrders(
        @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
        @Valid @RequestBody OrderSearchDTO orderSearchDTO
    ) {
        OrderSpecificationBuilder builder = new OrderSpecificationBuilder();
        List<SearchCriteria> criteriaList = orderSearchDTO.getSearchCriteriaList();

        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(orderSearchDTO.getDataOption());
                builder.with(x);
            });
        }

        Pageable page = buildPaginatedResponse(orderSearchDTO, builder, pageNum, pageSize);
        Page<Order> orderPage = orderService.findBySearchCriteria(builder.build(), page);

        return OrderController
            .<List<Order>>getResponseBuilder()
            .buildResponse(
                HttpStatus.OK.value(),
                "Resultados da busca por pedidos",
                orderPage.toList()
            );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> create(
        @Valid @RequestBody OrderCreateDTO payload,
        Errors errors
    ) {
        if (errors.hasErrors())
            return OrderController
                .getResponseBuilder()
                .buildResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Payload de criação de pedido inválida",
                    errors
                );

        OrderDTO created = this.orderService.create(payload);

        return OrderController
            .getResponseBuilder()
            .buildResponse(
                HttpStatus.OK.value(),
                "Pedido criado",
                created
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> update(
        @PathVariable Long id,
        @Valid @RequestBody(required = false) OrderUpdateDTO payload,
        Errors errors
    ) {
        if (errors.hasErrors())
            return OrderController
                .getResponseBuilder()
                .buildResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Payload de atualização de pedido inválida",
                    errors.hasErrors() ? errors : null
                );

        OrderDTO updated = this.orderService.update(id, payload);

        return OrderController
            .getResponseBuilder()
            .buildResponse(
                HttpStatus.OK.value(),
                "Pedido atualizado",
                updated
            );
    }
}
