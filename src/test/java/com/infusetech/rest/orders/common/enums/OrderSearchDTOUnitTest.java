package com.infusetech.rest.orders.common.enums;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.infusetech.rest.orders.filtering.orders.dto.OrderSearchDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class OrderSearchDTOUnitTest {
    public static Validator getValidatorInstance() {
        return Validation
            .buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenAllAcceptable_thenShouldNotGiveConstraintViolations() {
        OrderSearchDTO dto = new OrderSearchDTO();
        dto.setDataOption(SearchOperation.ALL);
        
        Set<ConstraintViolation<OrderSearchDTO>> violations = getValidatorInstance().validate(dto);
     
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenAllInvalid_thenViolationsShouldBeReported() {
        OrderSearchDTO dto = new OrderSearchDTO();
        dto.setDataOption(SearchOperation.NOT_NULL);

        Set<ConstraintViolation<OrderSearchDTO>> violations = getValidatorInstance().validate(dto);
        
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).anyMatch(
            havingPropertyPath("dataOption")
            .and(havingMessage("Deve ser um dos seguintes valores: [any, all]")
        ));
    }

    public static Predicate<ConstraintViolation<OrderSearchDTO>> havingMessage(String message) {
        return l -> message.equals(l.getMessage());            
    }

    public static Predicate<ConstraintViolation<OrderSearchDTO>> havingPropertyPath(String propertyPath) {
        return l -> propertyPath.equals(l.getPropertyPath().toString());
    }
}
