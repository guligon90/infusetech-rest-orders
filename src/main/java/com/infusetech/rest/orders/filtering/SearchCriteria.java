package com.infusetech.rest.orders.filtering;

import com.infusetech.rest.orders.common.enums.SearchOperation;
import com.infusetech.rest.orders.filtering.validators.SearchOperationSubset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String filterKey;
    private Object value;

    @SearchOperationSubset(anyOf = {
        SearchOperation.ALL,
        SearchOperation.ANY,
    })
    private SearchOperation dataOption;

    @SearchOperationSubset(anyOf = {
        SearchOperation.CONTAINS,
        SearchOperation.DOES_NOT_CONTAIN,
        SearchOperation.EQUAL,
        SearchOperation.NOT_EQUAL,
        SearchOperation.BEGINS_WITH,
        SearchOperation.DOES_NOT_BEGIN_WITH,
        SearchOperation.ENDS_WITH,
        SearchOperation.DOES_NOT_END_WITH,
        SearchOperation.NUL,
        SearchOperation.NOT_NULL,
        SearchOperation.GREATER_THAN,
        SearchOperation.GREATER_THAN_EQUAL,
        SearchOperation.LESS_THAN,
        SearchOperation.LESS_THAN_EQUAL,
    })
    private SearchOperation operation;

    public SearchCriteria(String filterKey, SearchOperation operation, Object value) {
        super();
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
    }
}
