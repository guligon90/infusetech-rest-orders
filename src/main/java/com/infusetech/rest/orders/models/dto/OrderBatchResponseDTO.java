package com.infusetech.rest.orders.models.dto;

import java.util.List;

public class OrderBatchResponseDTO {
    private List<OrderDTO> orders;
    private OrderBatchErrorsDTO errors;

    public OrderBatchResponseDTO() {
    }

    public List<OrderDTO> getOrders() {
        return this.orders;
    }

    public OrderBatchErrorsDTO getErrors() {
        return this.errors;
    }

    public OrderBatchResponseDTO(List<OrderDTO> orders, OrderBatchErrorsDTO errors) {
        this.orders = orders;
        this.errors = errors;
    }

    public static class Builder {
        private List<OrderDTO> orders;
        private OrderBatchErrorsDTO errors;

        public Builder withOrders(List<OrderDTO> orders) {
            this.orders = orders;

            return this;
        }

        public Builder withErrors(OrderBatchErrorsDTO errors) {
            this.errors = errors;

            return this;
        }

        public OrderBatchResponseDTO build() {
            return new OrderBatchResponseDTO(orders, errors);
        }
    }
}
