package com.preonboarding.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PagingUtil {
    public Sort getPagingSort(String sorting) {
        if (sorting == null || sorting.isEmpty()) {
            return Sort.by(Sort.Order.desc("createdAt"));
        }

        String[] parts = sorting.split(":");

        String field = mapField(parts[0].trim());
        String direction = parts[1].trim().toLowerCase();

        Sort.Order order = direction.equals("asc")
                ? Sort.Order.asc(field)
                : Sort.Order.desc(field);

        return Sort.by(order);
    }

    private String mapField(String field) {
        return switch (field) {
            case "updated_at" -> "updatedAt";
            case "rating" -> "rating";
            case "helpful_votes" -> "helpfulVotes";
            default -> "createdAt";
        };
    }
}
