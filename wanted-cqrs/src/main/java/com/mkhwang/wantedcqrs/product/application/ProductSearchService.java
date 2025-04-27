package com.mkhwang.wantedcqrs.product.application;


import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.product.domain.Category;
import com.mkhwang.wantedcqrs.product.domain.ProductCategory;
import com.mkhwang.wantedcqrs.product.domain.dto.*;
import com.mkhwang.wantedcqrs.product.infra.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
  private final ProductRepository productRepository;
  private final ProductSearchRepository productSearchRepository;
  private final ReviewSearchRepository reviewSearchRepository;
  private final ProductDetailRepository productDetailRepository;
  private final CategoryRepository categoryRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final ProductImageRepository productImageRepository;
  private final GenericMapper genericMapper;

  public Page<ProductSearchResultDto> searchProducts(ProductSearchDto searchDto) {
    return productSearchRepository.searchProducts(searchDto);
  }

  public ProductSearchDetailDto getProductDetailById(Long id) {
    ProductSearchDetailDto base = Optional.ofNullable(productSearchRepository.getProductDetailBaseById(id))
            .orElseThrow(
                    () -> new ResourceNotFoundException("product.detail.not.found", null)
            );

    base.setRating(reviewSearchRepository.findAverageRatingByProductId(id));
    productDetailRepository.findByProductId(id)
            .ifPresent(productDetail -> {
              base.setDetail(genericMapper.toDto(productDetail, ProductDetailDto.class));
            });
    base.setCategories(this.getProductCategories(id));
    base.setImages(productImageRepository.findByProductId(id).stream().map(
            productImage -> genericMapper.toDto(productImage, ProductImageDto.class)
    ).toList());
    base.setTags(productSearchRepository.findTagsByProductId(id));

    return base;
  }

  private List<ProductCategoryDto> getProductCategories(Long productId) {
    List<ProductCategoryDto> result = new ArrayList<>();

    List<ProductCategory> productCategories = productCategoryRepository.findByProductId(productId);
    List<Category> allCategories = categoryRepository.findAll();

    for (ProductCategory productCategory : productCategories) {
      Category category = allCategories.stream()
              .filter(c -> c.getId().equals(productCategory.getCategory().getId()))
              .findFirst()
              .orElse(null);
      if (category != null) {
        ProductCategoryDto dto = genericMapper.toDto(productCategory, ProductCategoryDto.class);
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setPrimary(productCategory.isPrimary());
        if (category.getParent() != null) {
          Category parent = category.getParent();
          dto.setParent(
                  ProductCategoryParentDto.of(parent.getId(), parent.getName(), parent.getSlug())
          );
        }

        result.add(dto);
      }
    }
    return result;
  }
}
