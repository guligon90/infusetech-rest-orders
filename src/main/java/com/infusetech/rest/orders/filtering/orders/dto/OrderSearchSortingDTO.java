package com.infusetech.rest.orders.filtering.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchSortingDTO {
    private String filterKey;
    private Boolean ascending;
}
