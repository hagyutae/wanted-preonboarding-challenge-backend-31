package lkdcode.wanted.ecommerce.framework.query;


import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.ParamCondition;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.QueryParamConditions;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class QueryBase<E, Q extends EntityPathBase<E>> {
    protected final JPAQueryFactory factory;
    protected final QueryDslUtil queryDslUtil;

    protected QueryBase(JPAQueryFactory factory, QueryDslUtil queryDslUtil) {
        this.factory = factory;
        this.queryDslUtil = queryDslUtil;
    }

    public long getTotalSize(final BooleanExpression... conditions) {
        return Optional.ofNullable(factory
            .select(getQClazz().count())
            .from(getQClazz())
            .where(conditions)
            .fetchOne()
        ).orElse(0L);
    }

    public BooleanExpression[] usingWhereCondition(final QueryParamConditions queryParamConditions) {
        return queryParamConditions.stream()
            .filter(notEmptyCondition())
            .map(paramFilterCondition())
            .filter(Objects::nonNull)
            .toArray(BooleanExpression[]::new);
    }

    public BooleanExpression[] usingWhereCondition(final QueryParamConditions queryParamConditions, final BooleanExpression... expressions) {
        return Stream.concat(
                queryParamConditions.stream()
                    .filter(notEmptyCondition())
                    .map(paramFilterCondition())
                    .filter(Objects::nonNull)
                    .toList()
                    .stream(),
                Arrays.stream(expressions)
                    .filter(Objects::nonNull)
                    .toList().stream())
            .toArray(BooleanExpression[]::new);
    }

    private static Predicate<ParamCondition<?>> notEmptyCondition() {
        return e -> e.keyNotEmpty() && e.valueNotEmpty();
    }

    public abstract Q getQClazz();

    protected abstract Function<String, Expression<? extends Comparable<?>>> sortingCondition();

    protected abstract Function<ParamCondition<?>, BooleanExpression> paramFilterCondition();
}