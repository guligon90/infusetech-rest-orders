package com.infusetech.rest.orders.filtering.orders.specification;

import com.infusetech.rest.orders.common.enums.SearchOperation;
import com.infusetech.rest.orders.common.filters.SearchCriteria;
import com.infusetech.rest.orders.models.Order;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecificationBuilder {
    private final List<SearchCriteria> params;

    public OrderSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final OrderSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        
        return this;
    }

    public final OrderSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        
        return this;
    }

    public Specification<Order> build() {
        if(params.size() == 0) return null;

        Specification<Order> result = new OrderSpecification(params.get(0));

        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                ? Specification.where(result).and(new OrderSpecification(criteria))
                : Specification.where(result).or(new OrderSpecification(criteria));
        }

        return result;
    }
}
