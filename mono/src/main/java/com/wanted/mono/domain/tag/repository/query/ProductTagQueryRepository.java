package com.wanted.mono.domain.tag.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.mono.domain.tag.entity.ProductTag;
import com.wanted.mono.domain.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.mono.domain.tag.entity.QProductTag.productTag;
import static com.wanted.mono.domain.tag.entity.QTag.tag;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductTagQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductTag> findProductTagsByProductIdWithTag(final Long productId) {
        return queryFactory.select(productTag)
                .from(productTag)
                .join(productTag.tag, tag).fetchJoin()
                .where(productTag.product.id.eq(productId))
                .fetch();
    }
}
