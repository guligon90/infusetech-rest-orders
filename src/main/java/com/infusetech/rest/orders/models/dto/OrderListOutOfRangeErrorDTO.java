package com.infusetech.rest.orders.models.dto;

public class OrderListOutOfRangeErrorDTO {
    private final int size;
    private final String message;

    public OrderListOutOfRangeErrorDTO(int size, String message) {
        this.size = size;
        this.message = message;
    }

    public int getSize() {
        return this.size;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + size + ", " + message + ")";
    }
}