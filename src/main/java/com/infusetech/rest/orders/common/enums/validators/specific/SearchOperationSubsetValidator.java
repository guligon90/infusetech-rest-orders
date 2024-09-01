package com.infusetech.rest.orders.common.enums.validators.specific;

import java.util.Arrays;

import com.infusetech.rest.orders.common.enums.SearchOperation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SearchOperationSubsetValidator implements ConstraintValidator<SearchOperationSubset, SearchOperation> {
    private SearchOperation[] subset;

    @Override
    public void initialize(SearchOperationSubset constraint) {
        this.subset = constraint.anyOf();        
    }

    @Override
    public boolean isValid(SearchOperation value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}