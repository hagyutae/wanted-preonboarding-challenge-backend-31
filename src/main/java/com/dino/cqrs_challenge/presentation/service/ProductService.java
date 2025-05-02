package com.dino.cqrs_challenge.presentation.service;

import com.dino.cqrs_challenge.domain.entity.Brand;
import com.dino.cqrs_challenge.domain.entity.Category;
import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductCategory;
import com.dino.cqrs_challenge.domain.entity.ProductDetail;
import com.dino.cqrs_challenge.domain.entity.ProductImage;
import com.dino.cqrs_challenge.domain.entity.ProductOption;
import com.dino.cqrs_challenge.domain.entity.ProductOptionGroup;
import com.dino.cqrs_challenge.domain.entity.ProductPrice;
import com.dino.cqrs_challenge.domain.entity.ProductTag;
import com.dino.cqrs_challenge.domain.entity.Seller;
import com.dino.cqrs_challenge.domain.entity.Tag;
import com.dino.cqrs_challenge.domain.repository.BrandRepository;
import com.dino.cqrs_challenge.domain.repository.CategoryRepository;
import com.dino.cqrs_challenge.domain.repository.ProductCategoryRepository;
import com.dino.cqrs_challenge.domain.repository.ProductDetailRepository;
import com.dino.cqrs_challenge.domain.repository.ProductImageRepository;
import com.dino.cqrs_challenge.domain.repository.ProductOptionGroupRepository;
import com.dino.cqrs_challenge.domain.repository.ProductOptionRepository;
import com.dino.cqrs_challenge.domain.repository.ProductPriceRepository;
import com.dino.cqrs_challenge.domain.repository.ProductRepository;
import com.dino.cqrs_challenge.domain.repository.ProductTagRepository;
import com.dino.cqrs_challenge.domain.repository.SellerRepository;
import com.dino.cqrs_challenge.domain.repository.TagRepository;
import com.dino.cqrs_challenge.presentation.exception.BrandNotFoundException;
import com.dino.cqrs_challenge.presentation.exception.CategoryNotFoundException;
import com.dino.cqrs_challenge.presentation.exception.SellerNotFoundException;
import com.dino.cqrs_challenge.presentation.exception.TagNotFoundException;
import com.dino.cqrs_challenge.presentation.model.dto.SaveProductCategoryDTO;
import com.dino.cqrs_challenge.presentation.model.dto.SaveProductDetailDTO;
import com.dino.cqrs_challenge.presentation.model.dto.SaveProductImageDTO;
import com.dino.cqrs_challenge.presentation.model.dto.SaveProductOptionGroupDTO;
import com.dino.cqrs_challenge.presentation.model.dto.SaveProductPriceDTO;
import com.dino.cqrs_challenge.presentation.model.rq.CreateProductImageRequest;
import com.dino.cqrs_challenge.presentation.model.rq.CreateProductOptionRequest;
import com.dino.cqrs_challenge.presentation.model.rq.CreateProductRequest;
import com.dino.cqrs_challenge.presentation.model.rq.UpdateProductOptionRequest;
import com.dino.cqrs_challenge.presentation.model.rq.UpdateProductRequest;
import com.dino.cqrs_challenge.presentation.model.rs.CreateProductImageResponse;
import com.dino.cqrs_challenge.presentation.model.rs.CreateProductOptionResponse;
import com.dino.cqrs_challenge.presentation.model.rs.CreateProductResponse;
import com.dino.cqrs_challenge.presentation.model.rs.UpdateProductOptionResponse;
import com.dino.cqrs_challenge.presentation.model.rs.UpdateProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductTagRepository productTagRepository;
    private final TagRepository tagRepository;

    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {

        Seller seller = sellerRepository.findById(createProductRequest.getSellerId()).orElseThrow(SellerNotFoundException::new);
        Brand brand = brandRepository.findById(createProductRequest.getBrandId()).orElseThrow(BrandNotFoundException::new);

        Product product = createProductRequest.toEntity(seller, brand);
        productRepository.save(product);
        
        saveProductDetail(product, createProductRequest.getDetail());
        saveProductPrice(product, createProductRequest.getPrice());
        saveProductCategoryList(product, createProductRequest.getCategories());
        saveProductOptionGroupList(product, createProductRequest.getOptionGroups());
        saveProductImageList(product, createProductRequest.getImages());
        saveProductTagList(product, createProductRequest.getTags());
        productRepository.save(product);

        return CreateProductResponse.create(product);
    }

    private void saveProductTagList(Product product, List<Long> tagIdList) {
        List<Tag> tagList = tagRepository.findAllById(tagIdList);
        Map<Long, Tag> tagMap = tagList.stream()
                .collect(Collectors.toMap(Tag::getId, Function.identity(), (o1, o2) -> o1));
        List<ProductTag> productTagList = tagIdList.stream()
                .map(tagId -> {
                    Tag tag = tagMap.get(tagId);
                    if (tag == null) {
                        throw new TagNotFoundException();
                    }
                    ProductTag productTag = new ProductTag();
                    productTag.setProduct(product);
                    productTag.setTag(tag);
                    return productTag;
                }).toList();
        productTagRepository.saveAll(productTagList);
    }

    private void saveProductOptionGroupList(Product product, List<SaveProductOptionGroupDTO> optionGroupDtoList) {
        for (SaveProductOptionGroupDTO saveProductOptionGroupDTO : optionGroupDtoList) {
            ProductOptionGroup productOptionGroup = saveProductOptionGroupDTO.toEntity(product);
            productOptionGroupRepository.save(productOptionGroup);
            List<ProductOption> productOptionList = saveProductOptionGroupDTO.getOptions().stream()
                    .map(option -> option.toEntity(productOptionGroup))
                    .toList();
            productOptionRepository.saveAll(productOptionList);
        }
    }

    private void saveProductPrice(Product product, SaveProductPriceDTO priceDto) {
        ProductPrice productPrice = priceDto.toEntity(product);
        productPriceRepository.save(productPrice);
    }

    private void saveProductDetail(Product product, SaveProductDetailDTO detailDto) {
        ProductDetail productDetail = detailDto.toEntity(product);
        productDetailRepository.save(productDetail);
    }

    private void saveProductImageList(Product product, List<SaveProductImageDTO> imageDtoList) {
        List<ProductImage> productImageList = imageDtoList.stream()
                .map(toEntity -> toEntity.toEntity(product))
                .toList();
        productImageRepository.saveAll(productImageList);
    }

    private void saveProductCategoryList(Product product, List<SaveProductCategoryDTO> categoryDtoList) {
        List<Long> categoryIdList = categoryDtoList.stream()
                .map(SaveProductCategoryDTO::getCategoryId).toList();

        Map<Long, Category> categoryMap = categoryRepository.findAllByIdIn(categoryIdList)
                .stream()
                .collect(Collectors.toMap(
                        Category::getId,
                        Function.identity(),
                        (o1, o2) -> o1)
                );

        List<ProductCategory> productCategoryList = categoryDtoList
                .stream()
                .map(categoryDTO -> {
                    Category category = categoryMap.get(categoryDTO.getCategoryId());
                    if (category == null) {
                        throw new CategoryNotFoundException();
                    }
                    return categoryDTO.toEntity(product, category);
                })
                .toList();
        productCategoryRepository.saveAll(productCategoryList);
    }

    public UpdateProductResponse updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
        // TODO 상품 수정 로직 구현
        return new UpdateProductResponse();
    }

    public void deleteProduct(Integer id) {
        // TODO 상품 삭제 로직 구현
    }

    public CreateProductOptionResponse addProductOption(Integer id, CreateProductOptionRequest createProductOptionRequest) {
        // TODO 상품 옵션 추가 로직 구현
        return new CreateProductOptionResponse();
    }

    public UpdateProductOptionResponse updateProductOption(Integer id, Integer optionId, UpdateProductOptionRequest updateProductOptionRequest) {
        // TODO 상품 옵션 수정 로직 구현
        return new UpdateProductOptionResponse();
    }

    public void deleteProductOption(Integer id, Integer optionId) {
        // TODO 상품 옵션 삭제 로직 구현
    }

    public CreateProductImageResponse addProductImage(Integer id, CreateProductImageRequest productImageRequest) {
        // TODO 상품 이미지 추가 로직 구현
        return new CreateProductImageResponse();
    }
}
