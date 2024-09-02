package com.infusetech.rest.orders.models.dto;

import java.util.List;

public class OrderBatchErrorsDTO {
    private OrderListOutOfRangeErrorDTO ordersListOutOfRangeError;
    private List<List<OrderConstraintViolationDTO>> fieldsViolations;

    public OrderBatchErrorsDTO() {
    }

    public OrderBatchErrorsDTO(
        OrderListOutOfRangeErrorDTO ordersListOutOfRangeError,
        List<List<OrderConstraintViolationDTO>> fieldsViolations
    ) {
        this.ordersListOutOfRangeError = ordersListOutOfRangeError;
        this.fieldsViolations = fieldsViolations;
    }

    public OrderListOutOfRangeErrorDTO getOrderListOutOfRangeErrorDTO() {
        return this.ordersListOutOfRangeError;
    }

    public List<List<OrderConstraintViolationDTO>> getOrdersErrors() {
        return this.fieldsViolations;
    }

    @Override
    public String toString() {
        String representation = "\n" + this.getClass().getName() + "(";

        if (ordersListOutOfRangeError != null)
            representation += "\n\t" + ordersListOutOfRangeError.toString() + ",";

        if (fieldsViolations != null) {
            representation += "\n\tfieldsViolations=[";

            for (List<OrderConstraintViolationDTO> item : fieldsViolations) {
                representation += "\n\t\t" + item.toString() + ",";
            }

            representation += "\n\t],";
        }

        return representation + ")\n";
    }

    public static class Builder {
        private OrderListOutOfRangeErrorDTO ordersListOutOfRangeError;
        private List<List<OrderConstraintViolationDTO>> fieldsViolations;

        public Builder withOrderListOutOfRangeErrorDTO(OrderListOutOfRangeErrorDTO error) {
            this.ordersListOutOfRangeError = error;

            return this;
        }

        public Builder withFieldsViolations(List<List<OrderConstraintViolationDTO>> violations) {
            this.fieldsViolations = violations;

            return this;
        }

        public OrderBatchErrorsDTO build() {
            return new OrderBatchErrorsDTO(ordersListOutOfRangeError, fieldsViolations);
        }
    }
}
