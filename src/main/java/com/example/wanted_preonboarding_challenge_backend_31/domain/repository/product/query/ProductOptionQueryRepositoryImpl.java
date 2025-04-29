package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.query;

import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.QProductOption.productOption;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOption;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.QuerydslRepositorySupport;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionDetailDto;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ProductOptionQueryRepositoryImpl extends QuerydslRepositorySupport implements
        ProductOptionQueryRepository {

    protected ProductOptionQueryRepositoryImpl() {
        super(ProductOption.class);
    }

    @Override
    public Map<Long, List<ProductOptionDetailDto>> getProductOptionDetails(List<Long> productOptionGroupIds) {
        return queryFactory()
                .select(productOption.productOptionGroup.id,
                        productOption.id,
                        productOption.name,
                        productOption.additionalPrice,
                        productOption.sku,
                        productOption.stock,
                        productOption.displayOrder)
                .from(productOption)
                .where(productOption.productOptionGroup.id.in(productOptionGroupIds))
                .transform(GroupBy.groupBy(productOption.productOptionGroup.id).as(
                        GroupBy.list(
                                Projections.constructor(
                                        ProductOptionDetailDto.class,
                                        productOption.id,
                                        productOption.name,
                                        productOption.additionalPrice,
                                        productOption.sku,
                                        productOption.stock,
                                        productOption.displayOrder
                                )
                        )
                ));
    }
}
