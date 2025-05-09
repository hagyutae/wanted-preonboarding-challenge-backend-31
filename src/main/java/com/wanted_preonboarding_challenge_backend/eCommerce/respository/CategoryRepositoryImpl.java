package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom{
    @Override
    public CategoryDto findCategoryWithParent(Long id) {
//        QCategory c = QCategory.category;
//        QCategory p = new QCategory("parent");
//
//        return queryFactory
//                .select(Projections.constructor(CategoryDto.class,
//                        c.id,
//                        c.name,
//                        c.slug,
//                        c.description,
//                        c.level,
//                        c.imageUrl,
//                        Projections.constructor(CategoryDto.ParentCategoryDto.class,
//                                p.id,
//                                p.name,
//                                p.slug
//                        )
//                ))
//                .from(c)
//                .leftJoin(p).on(c.parent.id.eq(p.id))
//                .where(c.id.eq(id));
        return null;
    }
}
