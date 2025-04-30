package com.example.preonboarding.service;

import com.example.preonboarding.domain.*;
import com.example.preonboarding.dto.*;
import com.example.preonboarding.repository.categories.CategoriesRepository;
import com.example.preonboarding.repository.products.ProductRepository;
import com.example.preonboarding.repository.products.ProductRepositoryCustom;
import com.example.preonboarding.repository.reviews.RatingRepository;
import com.example.preonboarding.response.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepositoryCustom productRepositoryCustom;
    private final RatingRepository ratingRepository;
    private final CategoriesRepository categoriesRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public List<ProductsSummaryResponse> findAllProducts(ProductSearchRequest search){
        List<ProductsDTO> products = productRepositoryCustom.searchPage(search);

        return products.stream()
                .map(o -> ProductsSummaryResponse.builder()
                        .id(o.getId())
                        .name(o.getName())
                        .slug(o.getSlug())
                        .shortDescription(o.getShortDescription())
                        .basePrice(o.getBasePrice())
                        .salesPrice(o.getSalesPrice())
                        .currency(o.getCurrency())
                        .primaryImage(ImageResponse.builder()
                                .url(o.getImages().getUrl())
                                .altText(o.getImages().getAltText()).build())
                        .brand(BrandResponse.builder()
                                .id(o.getBrandId())
                                .name(o.getBrandName())
                                .build())
                        .seller(SellerResponse.builder()
                                .id(o.getSellerId())
                                .name(o.getSellerName())
                                .build())
                        .rating(o.getReviews().getRating())
                        .reviewCount(o.getReviews().getReviewCount())
                        .inStock(o.getOptions().isStock())
                        .status(o.getStatus())
                        .createdAt(o.getCreatedAt())
                        .build()
                ).collect(Collectors.toList());
    }

    public ProductsDetailResponse findProductsById(Long id){
        Products product = productRepositoryCustom.findProductsById(id);

        ProductDetails productDetails = product.getProductDetails();

        Dimensions dimesions = new Dimensions();
        AdditionalInfo additionalInfo = new AdditionalInfo();

        try {
            if (productDetails.getDimensions() != null) {
                dimesions = objectMapper.readValue(productDetails.getDimensions(), Dimensions.class);

            }
        }catch (IOException e){
            throw new RuntimeException("dimensions 파싱 에러", e);
        }

        try {
            if (productDetails.getAdditionalInfo() != null) {
                additionalInfo = objectMapper.readValue(productDetails.getAdditionalInfo(), AdditionalInfo.class);
            }

        }catch (IOException e){
            throw new RuntimeException("additionalInfo 파싱 에러", e);
        }

        DetailDTO detail = DetailDTO.builder()
                .weight(productDetails.getWeight())
                .dimensions(dimesions)
                .materials(productDetails.getMaterials())
                .countryOfOrigin(productDetails.getCountryOfOrigin())
                .warrantyInfo(productDetails.getWarrantyInfo())
                .careInstructions(productDetails.getCareInstructions())
                .additionalInfo(additionalInfo)
                .build();


        ProductPrices productPrices = product.getProductPrices();
        PriceDTO price = PriceDTO.builder()
                .basePrice(productPrices.getBasePrice())
                .salePrice(productPrices.getSalePrice())
                .costPrice(productPrices.getCostPrice())
                .currency(productPrices.getCurrency())
                .tax_rate(productPrices.getTax_rate())
                .build();



        List<ProductCategories> productCategories = product.getProductCategories();

        List<Long> parentIds = productCategories.stream()
                .map(i -> i.getCategories().getParentId())
                .distinct()
                .collect(Collectors.toList());

        Map<Long, Categories> parentCategoryMap = categoriesRepository.findAllById(parentIds).stream()
                .collect(Collectors.toMap(Categories::getId, Function.identity()));

        List<ProductCategoriesDTO> categories = productCategories.stream()
                .map(i -> {
                    return new ProductCategoriesDTO(i.getCategories(),
                            i.isPrimary(),
                            parentCategoryMap.get(i.getCategories().getParentId())) ;
                })
                .collect(Collectors.toList());



        List<ProductOptionGroup> productOptionGroups = product.getProductOptionGroups();
        List<ProductOptionDTO> optionDTOList = productOptionGroups.stream().map(i -> {
            return new ProductOptionDTO(i);
        }).collect(Collectors.toList());


        List<ProductImages> productImages = product.getProductImages();
        List<ProductImageDTO> images = productImages.stream().map(ProductImageDTO::new).collect(Collectors.toList());

        RatingDTO rating = ratingRepository.getRatingSummary(product.getId());



        List<ProductTags> productTags = product.getProductTags();

        List<TagsDTO> tags = productTags.stream().map(i -> {
            return TagsDTO.builder()
                    .id(i.getTags().getId())
                    .name(i.getTags().getName())
                    .slug(i.getTags().getSlug())
                    .build();


        }).collect(Collectors.toList());
        return ProductsDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .seller(new SellerResponse(product.getSellers()))
                .brand(new BrandResponse(product.getBrands()))
                .detail(detail)
                .price(price)
                .categories(categories)
                .optionGroups(optionDTOList)
                .images(images)
                .tags(tags)
                .rating(rating)
                .build();

    }
}
