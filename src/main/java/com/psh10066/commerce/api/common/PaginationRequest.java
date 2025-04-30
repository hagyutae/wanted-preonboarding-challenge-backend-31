package com.psh10066.commerce.api.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class PaginationRequest {

    int page = 1;
    int perPage = 10;
    List<String> sort = List.of("created_at:desc");

    public Pageable toPageable() {
        List<Sort.Order> orders = sort.stream()
            .map(s -> {
                String[] split = s.split(":");
                return new Sort.Order(Sort.Direction.fromString(split[1]), split[0]);
            })
            .toList();

        return PageRequest.of(page - 1, perPage, Sort.by(orders));
    }
}
