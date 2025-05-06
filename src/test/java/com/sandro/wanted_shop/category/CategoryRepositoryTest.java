package com.sandro.wanted_shop.category;

import com.sandro.wanted_shop.config.IntegrationTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryRepositoryTest extends IntegrationTestContext {
    @Autowired CategoryRepository categoryRepository;

    @DisplayName("카테고리를 계층 구조로 조회한다.")
    @Test
    void findAllWithChildren() throws Exception {
        List<Category> level1 = categoryRepository.findAllWithChildren();

        assertThat(level1).hasSize(8);

        List<Category> level2 = level1.stream()
                .map(Category::getChildren)
                .flatMap(Collection::stream)
                .toList();

        assertThat(level2).hasSize(36);

        List<Category> level3 = level2.stream()
                .map(Category::getChildren)
                .flatMap(Collection::stream)
                .toList();

        assertThat(level3).hasSize(39);
    }
}