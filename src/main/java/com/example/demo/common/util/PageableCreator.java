package com.example.demo.common.util;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

import static java.util.Objects.nonNull;

@NoArgsConstructor
public class PageableCreator {

    public static Pageable create(int page, int size, List<String> sortList) {
        Sort sort = Sort.unsorted();
        if (nonNull(sortList) && !sortList.isEmpty()) {
            sort = Sort.by(
                    sortList.stream().map(sortOption -> sortOption.split(":"))
                            .map(parts -> {
                                String field = parts[0];
                                Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
                                        ? Sort.Direction.DESC : Sort.Direction.ASC;
                                return new Sort.Order(direction, field);
                            })
                            .toList()
            );
        }

        return PageRequest.of(page - 1, size, sort);
    }
}
