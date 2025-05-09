package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

public interface CategoryRepositoryCustom {
    CategoryDto findCategoryWithParent(Long id);
}
