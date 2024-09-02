package com.infusetech.rest.orders.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
import com.infusetech.rest.orders.filtering.SearchCriteria;
import com.infusetech.rest.orders.filtering.orders.dto.OrderSearchDTO;
import com.infusetech.rest.orders.filtering.orders.specification.OrderSpecificationBuilder;
import com.infusetech.rest.orders.models.dto.OrderBatchResponseDTO;
import com.infusetech.rest.orders.models.dto.OrderCreateBulkDTO;
import com.infusetech.rest.orders.models.dto.OrderCreateDTO;
import com.infusetech.rest.orders.models.dto.OrderDTO;
import com.infusetech.rest.orders.models.dto.OrderUpdateDTO;
import com.infusetech.rest.orders.pagination.OrderPagination;
import com.infusetech.rest.orders.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/order")
@Tag(name = "order", description = "API de pedidos")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private static <T> ResponseEntity<ApiResponse<T>> buildResponse(int statusCode, String message, T responseData) {
        return new ResponseBuilder<T>()
            .buildResponse(
                statusCode,
                message,
                responseData
            );
    }

    @Operation(
        summary = "Detalhes do pedido",
        description = "Método usado para obter detalhes de um pedido, dado um identificador válido."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> findById(@PathVariable Long id) {
        OrderDTO result = this.orderService.findById(id);

        return buildResponse(
            HttpStatus.OK.value(),
            "Detalhes do pedido",
            result
        );
    }

    @Operation(
        summary = "Remoção do pedido",
        description = "Método usado para remover um pedido, dado um identificador válido."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        this.orderService.delete(id);

        return buildResponse(
            HttpStatus.NO_CONTENT.value(),
            "Pedido removido com sucesso",
            null
        );
    }

    @Operation(
        summary = "Listagem de pedidos",
        description = "Método usado para realizar a listagem de pedidos, dados parâmetros de paginação e filtros válidos."
    )
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> searchOrders(
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

        Pageable pageRequest = OrderPagination.buildPageRequest(orderSearchDTO, pageNum, pageSize);
        Page<OrderDTO> orderPage = orderService.findBySearchCriteria(builder.build(), pageRequest);

        return buildResponse(
            HttpStatus.OK.value(),
            "Resultados da busca por pedidos",
            orderPage.toList()
        );
    }

    @Operation(
        summary = "Criação de pedido",
        description = "Método usado para criar um novo pedido, dados parâmetros válidos."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> create(
        @Valid @RequestBody OrderCreateDTO payload,
        Errors errors
    ) {
        if (errors.hasErrors())
            return buildResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Payload de criação de pedido inválida",
                errors
            );

        OrderDTO created = this.orderService.create(payload);

        return buildResponse(
            HttpStatus.CREATED.value(),
            "Pedido criado",
            created
        );
    }

    @Operation(
        summary = "Atualização de pedido",
        description = "Método usado para atualizar um pedido existente, dados um identificador e parâmetros válidos."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> update(
        @PathVariable Long id,
        @Valid @RequestBody(required = false) OrderUpdateDTO payload,
        Errors errors
    ) {
        if (errors.hasErrors())
            return buildResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Payload de atualização de pedido inválida",
                errors.hasErrors() ? errors : null
            );

        OrderDTO updated = this.orderService.update(id, payload);

        return buildResponse(
            HttpStatus.OK.value(),
            "Pedido atualizado",
            updated
        );
    }

    @Operation(
        summary = "Criação de pedidos em lote",
        description = "Método usado para criar vários pedidos, dada um lista de parâmetros válidos."
    )
    @PostMapping(
        path = "bulk",
        consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.TEXT_XML_VALUE
        }
    )
    public ResponseEntity<ApiResponse<OrderBatchResponseDTO>> bulkCreate(
        @RequestBody OrderCreateBulkDTO payload
    ) {
        OrderBatchResponseDTO processedBatch = orderService.createOrdersInBulk(payload);
        Boolean batchHasErrors = processedBatch.getErrors() != null;

        String message = batchHasErrors
            ? "Erros ao processar lote de pedidos"
            : "Pedidos criados";

        int statusCode = batchHasErrors
            ? HttpStatus.BAD_REQUEST.value()
            : HttpStatus.CREATED.value();

        return buildResponse(statusCode, message, processedBatch);
    }
}
