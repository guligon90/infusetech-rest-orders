package com.infusetech.rest.orders.pagination;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.infusetech.rest.orders.filtering.orders.dto.OrderSearchDTO;
import com.infusetech.rest.orders.filtering.orders.dto.OrderSearchSortingDTO;

public class OrderPagination {
    public static Sort buildSortingCriteria(List<OrderSearchSortingDTO> sortingCriteriaList) {
        // Incializa ordernador como os critérios do primeiro campo
        Sort sortingCriteria = Sort.by(sortingCriteriaList.get(0).getFilterKey());

        if (sortingCriteriaList.get(0).getAscending())
            sortingCriteria.ascending();
        else
            sortingCriteria.descending();

        // Aplica critérios de ordenação para os campos restantes
        for (int i = 1; i < sortingCriteriaList.size(); i++) {
            OrderSearchSortingDTO criteria = sortingCriteriaList.get(i);

            sortingCriteria.and(Sort.by(criteria.getFilterKey()));

            if (criteria.getAscending())
                sortingCriteria.descending();
            else
                sortingCriteria.descending();
        }

        return sortingCriteria;
    }

    public static Pageable buildPageRequest(
        OrderSearchDTO orderSearchDTO,
        int pageNum,
        int pageSize
    ) {
        List<OrderSearchSortingDTO> sortingCriteriaList = orderSearchDTO.getSearchSortingCriteriaList();
        Sort defaultSorter = Sort.by("id").ascending();

        // Tratamento caso não sejam informados critérios de ordenação no body
        Sort sorter = sortingCriteriaList != null
            ? (
                sortingCriteriaList.size() > 0
                    ? buildSortingCriteria(sortingCriteriaList)
                    : defaultSorter
            )
            : defaultSorter;

        return PageRequest.of(pageNum, pageSize, sorter);
    }
}
