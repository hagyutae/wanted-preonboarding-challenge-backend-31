package com.preonboarding.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PagingUtil {
    public int getPageNumber(Integer page) {
        return page!=null ? Math.max(0,page-1):0;
    }

    public int getPerPageCount(Integer perPage) {
        return perPage!=null ? perPage:10;
    }

    public Sort getReviewPagingSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by(Sort.Order.desc("createdAt"));
        }

        String[] parts = sort.split(":");

        String field = reviewMapField(parts[0].trim());
        String direction = parts[1].trim().toLowerCase();

        Sort.Order order = direction.equals("asc")
                ? Sort.Order.asc(field)
                : Sort.Order.desc(field);

        return Sort.by(order);
    }

    public Sort getProductPagingSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by(Sort.Order.desc("createdAt"));
        }

        String[] parts = sort.split(":");

        String field = productMapField(parts[0].trim());
        String direction = parts[1].trim().toLowerCase();

        Sort.Order order = direction.equals("asc")
                ? Sort.Order.asc(field)
                : Sort.Order.desc(field);

        return Sort.by(order);
    }

    private String reviewMapField(String field) {
        return switch (field) {
            case "updated_at" -> "updatedAt";
            case "rating" -> "rating";
            case "helpful_votes" -> "helpfulVotes";
            default -> "createdAt";
        };
    }

    private String productMapField(String field) {
        return switch (field) {
            case "updated_at" -> "updatedAt";
            case "name" -> "name";
            default -> "createdAt";
        };
    }
}
