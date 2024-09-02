package com.infusetech.rest.orders.services.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.infusetech.rest.orders.models.OrderConstraints;
import com.infusetech.rest.orders.models.dto.OrderBatchErrorsDTO;
import com.infusetech.rest.orders.models.dto.OrderConstraintViolationDTO;
import com.infusetech.rest.orders.models.dto.OrderCreateBulkDTO;
import com.infusetech.rest.orders.models.dto.OrderCreateDTO;
import com.infusetech.rest.orders.models.dto.OrderListOutOfRangeErrorDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class OrderServiceValidator {
    public static List<List<OrderConstraintViolationDTO>> extractOrdersFiedsViolations(OrderCreateBulkDTO payload) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        List<List<OrderConstraintViolationDTO>> ordersViolations = new ArrayList<List<OrderConstraintViolationDTO>>();
        List<OrderCreateDTO> orders = payload.getPedidos();

        for (OrderCreateDTO order : orders) {
            Set<ConstraintViolation<OrderCreateDTO>> violations = validator.validate(order);

            if (!violations.isEmpty()) {
                List<OrderConstraintViolationDTO> mappedOrderViolations = new ArrayList<OrderConstraintViolationDTO>();

                // Por uma questão de representação do relatório final de erros em JSON, aqui eu estou
                // fazendo um mapeamento de Set<ConstraintViolation<OrderCreateDTO>>
                // para uma lista de DTOs customizados, i.e. List<OrderConstraintViolationDTO>,
                // extraindo apenas o campo, mensagem de erro e valor inválido
                violations.forEach(violation -> {
                    mappedOrderViolations.add(
                        new OrderConstraintViolationDTO(
                            violation.getPropertyPath().toString(),
                            violation.getMessage(),
                            violation.getInvalidValue()
                        ));
                });

                ordersViolations.add(mappedOrderViolations);
            }
        }

        return ordersViolations;
    }

    public static OrderBatchErrorsDTO validateOrderBatch(OrderCreateBulkDTO payload) {
        // Construção do relatório de erro para a lista com tamanho inadequado
        if (payload.isPedidosListSizeOutOfRange())
            return new OrderBatchErrorsDTO
                .Builder()
                .withOrderListOutOfRangeErrorDTO(
                    new OrderListOutOfRangeErrorDTO(
                        payload.getPedidos().size(),
                        OrderConstraints.Create.LISTA_PEDIDOS_OUT_OF_RANGE_ERROR_MESSAGE
                    )
                )
                .build();

        List<List<OrderConstraintViolationDTO>> ordersViolations = extractOrdersFiedsViolations(payload);

        // Construção do relatório de erros para payloads individuais de pedidos
        if (!ordersViolations.isEmpty())
            return new OrderBatchErrorsDTO
                .Builder()
                .withFieldsViolations(ordersViolations)
                .build();

        return null;
    }
}
