package com.sandro.wanted_shop.mainpage;

import com.sandro.wanted_shop.category.CategoryDto;
import com.sandro.wanted_shop.category.CategoryRepository;
import com.sandro.wanted_shop.product.dto.ProductListDto;
import com.sandro.wanted_shop.product.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainPageService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public MainPageDto getMainPage() {
        Page<ProductListDto> products = productRepository.findAll(
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"))
                )
                .map(ProductListDto::from);

        List<CategoryDto> allWithChildren = categoryRepository.findAllWithChildren().stream()
                .map(CategoryDto::from)
                .toList();

        return MainPageDto.of(null, products.getContent(), allWithChildren);
    }
}
