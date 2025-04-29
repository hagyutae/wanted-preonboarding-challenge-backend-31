package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category.query;

import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.QCategory.category;
import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.QProductCategory.productCategory;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.QuerydslRepositorySupport;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryParentDto;
import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryQueryRepositoryImpl extends QuerydslRepositorySupport implements CategoryQueryRepository {

    protected CategoryQueryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public List<CategoryDetailDto> getCategoryDetailsByProductId(Long productId) {
        List<Tuple> results = queryFactory()
                .select(
                        category.id,
                        category.name,
                        category.slug,
                        productCategory.isPrimary,
                        category.parentCategory.id,
                        category.parentCategory.name,
                        category.parentCategory.slug
                )
                .from(category)
                .join(productCategory).on(productCategory.category.id.eq(category.id)
                        .and(productCategory.product.id.eq(productId)))
                .leftJoin(category.parentCategory)
                .fetch();

        return results.stream()
                .map(tuple -> {
                    Long parentId = tuple.get(category.parentCategory.id);
                    String parentName = tuple.get(category.parentCategory.name);
                    String parentSlug = tuple.get(category.parentCategory.slug);

                    CategoryParentDto parentDto = null;
                    if (parentId != null) {
                        parentDto = new CategoryParentDto(parentId, parentName, parentSlug);
                    }

                    return new CategoryDetailDto(
                            tuple.get(category.id),
                            tuple.get(category.name),
                            tuple.get(category.slug),
                            Boolean.TRUE.equals(tuple.get(productCategory.isPrimary)),
                            parentDto
                    );
                })
                .toList();
    }
}
