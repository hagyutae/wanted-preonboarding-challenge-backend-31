package com.mkhwang.wantedcqrs.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class PageRequestDto {
  private Integer page;
  private Integer perPage;
  private String sort;

  public Pageable toPageable() {
    int safePage = (this.page == null || this.page < 1) ? 1 : this.page;
    int safeSize = (this.perPage == null || this.perPage < 1) ? 10 : this.perPage;

    List<Sort.Order> orders = parseSorts();
    return orders.isEmpty()
            ? PageRequest.of(safePage - 1, safeSize)
            : PageRequest.of(safePage - 1, safeSize, Sort.by(orders));
  }

  private List<Sort.Order> parseSorts() {
    if (!StringUtils.hasText(this.sort)) return List.of(Sort.Order.desc("createdAt"));

    return Arrays.stream(this.sort.split(",")).map(s -> s.split(":"))
            .filter(parts -> parts.length == 2)
            .map(parts -> new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]))
            .toList();

  }
}
