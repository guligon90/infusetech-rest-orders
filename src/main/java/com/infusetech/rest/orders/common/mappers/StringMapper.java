package com.infusetech.rest.orders.common.mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StringMapper {
    public static List<String> mapCslToListOfString(
        String csl,
        Optional<Boolean> toLowerCase,
        Optional<Boolean> toUpperCase
    ) {
        return Arrays
            .asList(csl.split(",", -1))
            .stream()
            .map(item -> {
                if (toLowerCase.orElse(false)) item = item.toLowerCase();
                if (toUpperCase.orElse(false)) item = item.toUpperCase();

                return item.trim();
            })
            .toList();
    }

    public static List<Boolean> mapCslToListOfBoolean(String csl) {
        return Arrays
            .asList(csl.split(",", -1))
            .stream()
            .map(item -> Boolean.parseBoolean(item))
            .toList();
    }
}
