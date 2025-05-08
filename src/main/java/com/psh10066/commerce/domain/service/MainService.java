package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.dto.response.GetMainResponse;
import com.psh10066.commerce.domain.model.category.Category;
import com.psh10066.commerce.domain.model.category.CategoryRepository;
import com.psh10066.commerce.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public GetMainResponse getMain() {
        List<Category> featuredCategories = categoryRepository.findAllByLevel(1).subList(0, 2);
        return new GetMainResponse(
            productRepository.getNewProducts(),
            productRepository.getPopularProducts(),
            featuredCategories.stream()
                .map(category -> new GetMainResponse.CategoryDto(
                    category.getId(), category.getName(), category.getSlug(), category.getImageUrl(), productRepository.getCategoryProductCount(category.getId())
                ))
                .toList()
        );
    }
}
