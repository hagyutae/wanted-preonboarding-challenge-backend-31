package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.product.domain.*;
import com.mkhwang.wantedcqrs.product.domain.dto.product.*;
import com.mkhwang.wantedcqrs.product.infra.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRegisterService {
  private final ProductRepository productRepository;
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

    createProductCategories(productCreateDto.getCategories(), product);

    createProductOptionGroups(productCreateDto.getOptionGroups(), product);

    createProductImages(productCreateDto.getImages(), product);

    createProductTags(productCreateDto.getTags(), product);

    return genericMapper.toDto(product, ProductCreateResponseDto.class);
  }

  private void createProductTags(List<Long> tagIds, Product product) {
    List<Tag> allTags = tagRepository.findAllByIdIn(tagIds);
    allTags.forEach(
            tag -> {
              ProductTag productTag = new ProductTag();
              productTag.setProduct(product);
              productTag.setTag(tag);
              productTagRepository.save(productTag);
            }
    );
  }

  private void createProductImages(List<ProductImageCreateDto> images, Product product) {
    if (images == null || images.isEmpty()) {
      return;
    }

    images.forEach(
            imageDto -> {
              ProductImage productImage = genericMapper.toEntity(imageDto, ProductImage.class);
              productImage.setProduct(product);
              productImageRepository.save(productImage);
            }
    );
  }

  private void createProductOptionGroups(List<ProductOptionGroupCreateDto> optionGroups, Product product) {
    if (optionGroups == null || optionGroups.isEmpty()) {
      return;
    }

    optionGroups.forEach(
            optionGroupDto -> {
              ProductOptionGroup productOptionGroup = genericMapper.toEntity(optionGroupDto, ProductOptionGroup.class);
              productOptionGroup.setProduct(product);
              productOptionGroupRepository.save(productOptionGroup);

              List<ProductOptionCreateDto> options = optionGroupDto.getOptions();
              if (options != null) {
                options.forEach(
                        optionDto -> {
                          ProductOption productOption = genericMapper.toEntity(optionDto, ProductOption.class);
                          productOption.setGroup(productOptionGroup);
                          productOptionRepository.save(productOption);
                        }
                );
              }
            }
    );
  }

  private void createProductCategories(List<ProductCategoryCreateDto> categories, Product product) {
    if (categories == null || categories.isEmpty()) {
      return;
    }

    categories.forEach(
            categoryDto -> {
              Category category = categoryRepository.findById(categoryDto.getCategoryId())
                      .orElseThrow(() -> new ResourceNotFoundException("category.not.found"));
              ProductCategory productCategory = new ProductCategory();
              productCategory.setProduct(product);
              productCategory.setCategory(category);
              productCategory.setPrimary(categoryDto.isPrimary());
              productCategoryRepository.save(productCategory);
            }
    );
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

    modifyProductDetail(dto, product);

    modifyProductPrice(dto, product);

    modifyProductCategories(dto, product);

    modifyProductTags(dto, product);

    return genericMapper.toDto(product, ProductCreateResponseDto.class);
  }

  private void modifyProductPrice(ProductModifyRequestDto dto, Product product) {
    ProductPrice productPrice = productPriceRepository.findByProductId(product.getId()).orElseThrow(
            () -> new ResourceNotFoundException("product.price.not.found")
    );
    ProductPriceCreateDto price = dto.getPrice();
    productPrice.setBasePrice(price.getBasePrice());
    productPrice.setSalePrice(price.getSalePrice());
    productPrice.setCostPrice(price.getCostPrice());
    productPrice.setCurrency(price.getCurrency());
    productPrice.setTaxRate(price.getTaxRate());
  }

  private void modifyProductDetail(ProductModifyRequestDto dto, Product product) {
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
  }

  private void modifyProductTags(ProductModifyRequestDto dto, Product product) {
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
  }

  private void modifyProductCategories(ProductModifyRequestDto dto, Product product) {
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
  }
}
