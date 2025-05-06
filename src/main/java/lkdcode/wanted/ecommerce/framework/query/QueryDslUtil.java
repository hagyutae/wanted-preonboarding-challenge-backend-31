package lkdcode.wanted.ecommerce.framework.query;


import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public final class QueryDslUtil {

    private static final String SPLIT_REGEX = " ";
    private static final String LIKE_FIX = "%";

    public OrderSpecifier<?>[] createOrderSpecifier(
        final Pageable pageable,
        final Function<String, Expression<? extends Comparable<?>>> path
    ) {
        return pageable.getSort()
            .stream()
            .filter(Objects::nonNull)
            .map(order -> {
                val direction = order.isAscending() ? Order.ASC : Order.DESC;
                val property = order.getProperty();

                if (path == null) {
                    return null;
                }

                return new OrderSpecifier<>(direction, path.apply(property));
            })
            .filter(Objects::nonNull)
            .toArray(OrderSpecifier[]::new);
    }

    public OrderSpecifier<?>[] createOrderSpecifier(
        final Pageable pageable,
        final Function<String, Expression<? extends Comparable<?>>> path,
        final OrderSpecifier<?>... conditions
    ) {
        return Stream.concat(
            pageable.getSort()
                .stream()
                .filter(Objects::nonNull)
                .map(order -> {
                    val direction = order.isAscending() ? Order.ASC : Order.DESC;
                    val property = order.getProperty();

                    if (path == null) {
                        return null;
                    }

                    return new OrderSpecifier<>(direction, path.apply(property));
                })
                .filter(Objects::nonNull)
                .toList()
                .stream()
            , Arrays.stream(conditions)
                .filter(Objects::nonNull)
                .toList()
                .stream()
        ).toArray(OrderSpecifier[]::new);
    }

    public String likeByStart(final String value) {
        return Arrays.stream(value.split(SPLIT_REGEX))
            .map(e -> e + LIKE_FIX)
            .collect(Collectors.joining());
    }

    public String likeByEnd(final String value) {
        return Arrays.stream(value.split(SPLIT_REGEX))
            .map(e -> LIKE_FIX + e)
            .collect(Collectors.joining());
    }

    public String likeByAll(final String value) {
        return Arrays.stream(value.split(SPLIT_REGEX))
            .map(e -> LIKE_FIX + e + LIKE_FIX)
            .collect(Collectors.joining());
    }
}