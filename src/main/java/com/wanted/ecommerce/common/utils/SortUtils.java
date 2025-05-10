package com.wanted.ecommerce.common.utils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SortUtils {

    public static Map<String, String> createSortMap(String sortParam) {
        String[] sorts = sortParam.split(",");
        return Arrays.stream(sorts)
            .map(sort1 -> {
                String[] sortKeyValue = sort1.split(":");
                String key = sortKeyValue[0];
                String value = sortKeyValue[1];
                return new AbstractMap.SimpleEntry<>(key, value);
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
