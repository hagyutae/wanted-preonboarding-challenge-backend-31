package com.mkhwang.wantedcqrs.common.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class QuerydslUtil {

  public OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable, Class<?> entityClass, String alias) {
    PathBuilder<?> pathBuilder = new PathBuilder<>(entityClass, alias);

    return pageable.getSort().stream()
            .map(order -> {
              Order direction = order.isAscending() ? Order.ASC : Order.DESC;
              ComparableExpression<?> expression = pathBuilder.getComparable(order.getProperty(), Comparable.class);
              return new OrderSpecifier<>(direction, expression);
            })
            .toArray(OrderSpecifier<?>[]::new);
  }
}
