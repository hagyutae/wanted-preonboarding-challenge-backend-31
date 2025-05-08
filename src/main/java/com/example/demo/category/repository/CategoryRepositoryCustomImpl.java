package com.example.demo.category.repository;

import com.example.demo.category.dto.CategoryQueryFilter;
import com.example.demo.category.entity.CategoryEntity;
import com.example.demo.category.entity.QCategoryEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.demo.category.entity.QCategoryEntity.categoryEntity;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory qf;

    @Override
    public List<CategoryEntity> findAllByFilter(CategoryQueryFilter filter) {
        QCategoryEntity parentCategory = new QCategoryEntity("parentCategory");

        return qf.selectFrom(categoryEntity)
                .leftJoin(categoryEntity.parent, parentCategory).fetchJoin()
                .where(this.satisfyCondition(filter))
                .fetch();
    }

    private BooleanBuilder satisfyCondition(CategoryQueryFilter filter) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(filter.categoryId())) {
            booleanBuilder.and(categoryEntity.id.eq(filter.categoryId()));
        }

        if(Objects.nonNull(filter.categoryIds()) && !filter.categoryIds().isEmpty()) {
            booleanBuilder.and(categoryEntity.id.in(filter.categoryIds()));
        }

        if(Objects.nonNull(filter.parentId())) {
            booleanBuilder.and(categoryEntity.parent.id.eq(filter.parentId()));
        }

        if(Objects.nonNull(filter.level())) {
            booleanBuilder.and(categoryEntity.level.goe(filter.level()));
        }

        return booleanBuilder;
    }
}
