package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category.query;

import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.QCategory.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.QuerydslRepositorySupport;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategorySearchRes;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryQueryRepositoryImpl extends QuerydslRepositorySupport implements CategoryQueryRepository {

    protected CategoryQueryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public Map<Long, List<CategorySearchRes>> groupAllCategoriesByParentId(int startLevel) {
        return queryFactory()
                .select()
                .from(category)
                .join(category.parentCategory)
                .where(category.level.goe(startLevel))
                .transform(GroupBy.groupBy(category.parentCategory.id).as(
                        GroupBy.list(Projections.constructor(
                                CategorySearchRes.class,
                                category.id,
                                category.name,
                                category.slug,
                                category.description,
                                category.level,
                                category.imageUrl
                        ))
                ));
    }
}
