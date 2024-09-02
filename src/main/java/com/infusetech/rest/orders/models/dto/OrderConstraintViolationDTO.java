package com.infusetech.rest.orders.models.dto;

public class OrderConstraintViolationDTO {
    private final String fieldPath;
    private final String message;
    private final Object invalidValue;

    public OrderConstraintViolationDTO(String fieldPath, String message, Object invalidValue) {
        this.fieldPath = fieldPath;
        this.message = message;
        this.invalidValue = invalidValue;
    }

    public String getFieldPath() {
        return this.fieldPath;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getInvalidValue() {
        return this.invalidValue;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" +
            fieldPath + ", " +
            message + ", " +
            invalidValue + ")";
    }
}
