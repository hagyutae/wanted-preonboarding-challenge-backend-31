package com.mkhwang.wantedcqrs.product.application;


import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.product.domain.*;
import com.mkhwang.wantedcqrs.product.domain.dto.*;
import com.mkhwang.wantedcqrs.product.domain.dto.product.*;
import com.mkhwang.wantedcqrs.product.infra.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final SellerRepository sellerRepository;
  private final BrandRepository brandRepository;
  private final ProductPriceRepository productPriceRepository;
  private final ProductTagRepository productTagRepository;
  private final TagRepository tagRepository;
  private final ProductOptionGroupRepository productOptionGroupRepository;
  private final ProductOptionRepository productOptionRepository;

  public Page<ProductSearchResultDto> searchProducts(ProductSearchDto searchDto) {
    return productSearchRepository.searchProducts(searchDto);
  }

  public ProductSearchDetailDto getProductDetailById(Long id) {
    ProductSearchDetailDto base = Optional.ofNullable(productSearchRepository.getProductDetailBaseById(id))
            .orElseThrow(
                    () -> new ResourceNotFoundException("product.detail.not.found")
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

  @Transactional
  public void deleteProduct(Long id) {
    productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("product.not.found")
    );

    productRepository.deleteById(id);
  }

  @Transactional
  public ProductCreateResponseDto createProduct(ProductCreateRequestDto productCreateDto) {
    Product product = genericMapper.toEntity(productCreateDto, Product.class);

    sellerRepository.findById(productCreateDto.getSellerId()).ifPresent(product::setSeller);
    brandRepository.findById(productCreateDto.getBrandId()).ifPresent(product::setBrand);

    productRepository.save(product);

    ProductDetail productDetail = genericMapper.toEntity(productCreateDto.getDetail(), ProductDetail.class);
    productDetail.setProduct(product);
    productDetailRepository.save(productDetail);

    ProductPrice productPrice = genericMapper.toEntity(productCreateDto.getPrice(), ProductPrice.class);
    productPrice.setProduct(product);
    productPriceRepository.save(productPrice);

    List<ProductCategoryCreateDto> categories = productCreateDto.getCategories();
    if (categories != null) {
      for (ProductCategoryCreateDto categoryDto : categories) {
        Category category = categoryRepository.findById(categoryDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category.not.found"));
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(product);
        productCategory.setCategory(category);
        productCategory.setPrimary(categoryDto.isPrimary());
        productCategoryRepository.save(productCategory);
      }
    }

    List<ProductOptionGroupCreateDto> optionGroups = productCreateDto.getOptionGroups();
    if (optionGroups != null) {
      for (ProductOptionGroupCreateDto optionGroupDto : optionGroups) {
        ProductOptionGroup productOptionGroup = genericMapper.toEntity(optionGroupDto, ProductOptionGroup.class);
        productOptionGroup.setProduct(product);
        productOptionGroupRepository.save(productOptionGroup);

        List<ProductOptionCreateDto> options = optionGroupDto.getOptions();
        if (options != null) {
          for (ProductOptionCreateDto optionDto : options) {
            ProductOption productOption = genericMapper.toEntity(optionDto, ProductOption.class);
            productOption.setGroup(productOptionGroup);
            productOptionRepository.save(productOption);
          }
        }
      }
    }

    List<ProductImageCreateDto> images = productCreateDto.getImages();
    if (images != null) {
      for (ProductImageCreateDto imageDto : images) {
        ProductImage productImage = genericMapper.toEntity(imageDto, ProductImage.class);
        productImage.setProduct(product);
        productImageRepository.save(productImage);
      }
    }

    List<Tag> allTags = tagRepository.findAllByIdIn(productCreateDto.getTags());
    if (allTags != null) {
      for (Tag tag : allTags) {
        ProductTag productTag = new ProductTag();
        productTag.setProduct(product);
        productTag.setTag(tag);
        productTagRepository.save(productTag);
      }
    }

    return genericMapper.toDto(product, ProductCreateResponseDto.class);
  }

  @Transactional
  public ProductCreateResponseDto modifyProduct(ProductModifyRequestDto dto) {
    Product product = productRepository.findById(dto.getId()).orElseThrow(
            () -> new ResourceNotFoundException("product.not.found")
    );
    product.setName(dto.getName());
    product.setSlug(dto.getSlug());
    product.setShortDescription(dto.getShortDescription());
    product.setFullDescription(dto.getFullDescription());
    product.setStatus(dto.getStatus());
    sellerRepository.findById(dto.getSellerId()).ifPresent(product::setSeller);
    brandRepository.findById(dto.getBrandId()).ifPresent(product::setBrand);

    ProductDetail productDetail = productDetailRepository.findByProductId(product.getId()).orElseThrow(
            () -> new ResourceNotFoundException("product.detail.not.found")
    );
    ProductDetailCreateDto detail = dto.getDetail();
    productDetail.setWeight(detail.getWeight());
    productDetail.setDimensions(detail.getDimensions());
    productDetail.setMaterials(detail.getMaterials());
    productDetail.setWarrantyInfo(detail.getWarrantyInfo());
    productDetail.setCareInstructions(detail.getCareInstructions());
    productDetail.setCountryOfOrigin(detail.getCountryOfOrigin());
    productDetail.setAdditionalInfo(detail.getAdditionalInfo());

    ProductPrice productPrice = productPriceRepository.findByProductId(product.getId()).orElseThrow(
            () -> new ResourceNotFoundException("product.price.not.found")
    );
    ProductPriceCreateDto price = dto.getPrice();
    productPrice.setBasePrice(price.getBasePrice());
    productPrice.setSalePrice(price.getSalePrice());
    productPrice.setCostPrice(price.getCostPrice());
    productPrice.setCurrency(price.getCurrency());
    productPrice.setTaxRate(price.getTaxRate());

    List<ProductCategory> productCategories = productCategoryRepository.findByProductId(product.getId());
    List<ProductCategoryCreateDto> categories = dto.getCategories();

    if (categories != null) {
      for (ProductCategoryCreateDto categoryDto : categories) {
        Category category = categoryRepository.findById(categoryDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category.not.found"));
        ProductCategory productCategory = productCategories.stream()
                .filter(pc -> pc.getCategory().getId().equals(category.getId()))
                .findFirst()
                .orElse(null);
        if (productCategory != null) {
          productCategory.setPrimary(categoryDto.isPrimary());
          productCategoryRepository.save(productCategory);
        } else {
          ProductCategory newProductCategory = new ProductCategory();
          newProductCategory.setProduct(product);
          newProductCategory.setCategory(category);
          newProductCategory.setPrimary(categoryDto.isPrimary());
          productCategoryRepository.save(newProductCategory);
        }
      }
    }

    if (dto.getTags() == null || dto.getTags().isEmpty()) {
      productTagRepository.deleteByProductId(product.getId());
    } else {
      List<Tag> allTags = tagRepository.findAllByIdIn(dto.getTags());
      List<ProductTag> existingTags = productTagRepository.findByProductId(product.getId());
      if (allTags != null) {
        for (Tag tag : allTags) {
          if (existingTags.stream().noneMatch(pt -> pt.getTag().getId().equals(tag.getId()))) {
            ProductTag productTag = new ProductTag();
            productTag.setProduct(product);
            productTag.setTag(tag);
            productTagRepository.save(productTag);
          }
        }
      }
      productTagRepository.deleteByProductIdAndTagNotIn(product.getId(), allTags);
    }

    return genericMapper.toDto(product, ProductCreateResponseDto.class);
  }
}
