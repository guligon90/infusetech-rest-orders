package com.infusetech.rest.orders.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SearchOperation {
    CONTAINS("cn"),
    DOES_NOT_CONTAIN("nc"),
    EQUAL("eq"),
    NOT_EQUAL("ne"),
    BEGINS_WITH("bw"),
    DOES_NOT_BEGIN_WITH("bn"),
    ENDS_WITH("ew"),
    DOES_NOT_END_WITH("en"),
    NUL("nu"),
    NOT_NULL("nn"),
    GREATER_THAN("gt"),
    GREATER_THAN_EQUAL("ge"),
    LESS_THAN("lt"),
    LESS_THAN_EQUAL("le"),
    ANY("all"),
    ALL("any");

    private final String operation;

    SearchOperation(String operation){
        this.operation = operation;
    }

    @JsonValue
    public String getSearchOperation() {
        return operation;
    }

    @JsonCreator
    public static SearchOperation fromValue(String value) {
        for (SearchOperation contact : values()) {
            String currentOperation = contact.getSearchOperation();

            if (currentOperation.equals(value)) {
                return contact;
            }
        }

        // Retorna uma resposta com um status HTTP 400
       throw new IllegalArgumentException("Operação de busca '" + value + " 'inválida");
    }
}
