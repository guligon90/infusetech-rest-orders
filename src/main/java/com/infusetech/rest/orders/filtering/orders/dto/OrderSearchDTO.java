package com.infusetech.rest.orders.filtering.orders.dto;

import java.util.List;

import com.infusetech.rest.orders.common.enums.SearchOperation;
import com.infusetech.rest.orders.filtering.SearchCriteria;
import com.infusetech.rest.orders.filtering.validators.SearchOperationSubset;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchDTO {
    private List<SearchCriteria> searchCriteriaList;
    private List<OrderSearchSortingDTO> searchSortingCriteriaList;

    @SearchOperationSubset(anyOf = {
        SearchOperation.ALL,
        SearchOperation.ANY,
    })
    private SearchOperation dataOption;
}
