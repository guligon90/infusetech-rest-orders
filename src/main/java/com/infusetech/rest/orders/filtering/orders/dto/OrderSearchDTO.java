package com.infusetech.rest.orders.filtering.orders.dto;

import java.util.List;

import com.infusetech.rest.orders.common.filters.SearchCriteria;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchDTO {
    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;
}
