package com.infusetech.rest.orders.common.enums;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;

import com.infusetech.rest.orders.LocaleAwareUnitTest;
import com.infusetech.rest.orders.filtering.SearchCriteria;

public class SearchCriteriaUnitTest extends LocaleAwareUnitTest {

    public static Validator getValidatorInstance() {
        return Validation
            .buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenAllAcceptable_thenShouldNotGiveConstraintViolations() {
        SearchCriteria searchCriteria = new SearchCriteria();
        
        searchCriteria.setOperation(SearchOperation.GREATER_THAN);
        searchCriteria.setDataOption(SearchOperation.ANY);
        
        Set<ConstraintViolation<SearchCriteria>> violations = getValidatorInstance().validate(searchCriteria);
        
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenAllInvalid_thenViolationsShouldBeReported() {
        SearchCriteria searchCriteria = new SearchCriteria();

        searchCriteria.setOperation(SearchOperation.ALL);
        searchCriteria.setDataOption(SearchOperation.LESS_THAN);
        
        Set<ConstraintViolation<SearchCriteria>> violations = getValidatorInstance().validate(searchCriteria);

        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations).anyMatch(
            havingPropertyPath("operation")
            .and(havingMessage( 
                "Deve ser um dos seguintes valores: [cn, nc, eq, ne, bw, bn, ew, en, nu, nn, gt, ge, lt, le]"
            ))
        );
        assertThat(violations).anyMatch(
            havingPropertyPath("dataOption")
            .and(havingMessage("Deve ser um dos seguintes valores: [any, all]"))
        );
    }

    public static Predicate<ConstraintViolation<SearchCriteria>> havingMessage(String message) {
        return l -> message.equals(l.getMessage());
    }

    public static Predicate<ConstraintViolation<SearchCriteria>> havingPropertyPath(String propertyPath) {
        return l -> propertyPath.equals(l.getPropertyPath().toString());
    }
}