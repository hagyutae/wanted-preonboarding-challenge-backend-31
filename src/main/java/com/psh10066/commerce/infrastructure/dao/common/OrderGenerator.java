package com.psh10066.commerce.infrastructure.dao.common;

import com.psh10066.commerce.util.StringUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class OrderGenerator {

    public static OrderSpecifier[] generateOrders(Sort sort, Class<?> clazz) {
        return sort.stream()
            .map(order ->
                new OrderSpecifier(
                    order.getDirection().isAscending() ? Order.ASC : Order.DESC,
                    Expressions.path(Object.class, new PathBuilder<>(clazz, StringUtil.pascalToCamel(clazz.getSimpleName())), StringUtil.snakeToCamel(order.getProperty()))
                ))
            .toArray(OrderSpecifier[]::new);
    }
}
