package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Products;
import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    // GET /api/products: 상품 목록 조회 (핕터 조건 적용, 정렬, 페이지네이션)
    public List<ProductInfoDto> getAllProducts() {
        List<Map<String, Object>> rawData = productsRepository.findAllProductDetails();
        return processProductData(rawData);
    }
    // GET /api/products/{id}: 상품 상세 조회 (모든 관련 정보 포함)
    public ProductInfoDto getProductById(Integer productId) {
        List<Map<String, Object>> rawData = productsRepository.findProductDetailsById(productId);
        if (rawData.isEmpty()) {
            return null;
        }

        List<ProductInfoDto> products = processProductData(rawData);
        return products.isEmpty() ? null : products.get(0);
    }

    private List<ProductInfoDto> processProductData(List<Map<String, Object>> rawData) {
        Map<Long, ProductInfoDto> productsMap = new HashMap<>();

        for (Map<String, Object> row : rawData) {
            Long productId = (Long) row.get("id");

            // 이미 처리한 제품인지 확인
            ProductInfoDto product = productsMap.get(productId);
            if (product == null) {
                product = new ProductInfoDto();

                // 기본 제품 정보 설정
                product.setId(productId);
                product.setName((String) row.get("name"));
                product.setSlug((String) row.get("slug"));
                product.setShortDescription((String) row.get("short_description"));
                product.setFullDescription((String) row.get("full_description"));
                product.setCreatedAt(convertToLocalDateTime(row.get("created_at")));
                product.setUpdatedAt(convertToLocalDateTime(row.get("updated_at")));
                product.setStatus((String) row.get("status"));

                // 판매자 정보 설정
                if (row.get("seller_id") != null) {
                    SellerDto seller = new SellerDto();
                    seller.setId((Long) row.get("seller_id"));
                    seller.setName((String) row.get("seller_name"));
                    seller.setDescription((String) row.get("seller_description"));
                    seller.setLogoUrl((String) row.get("seller_logo_url"));
                    seller.setRating(convertToDouble(row.get("seller_ratings")));
                    seller.setContactEmail((String) row.get("seller_contact_email"));
                    seller.setCreatedAt(convertToLocalDateTime(row.get("seller_created_at")));
                    product.setSeller(seller);
                }

                // 브랜드 정보 설정
                if (row.get("brand_id") != null) {
                    BrandDto brand = new BrandDto();
                    brand.setId((Long) row.get("brand_id"));
                    brand.setName((String) row.get("brand_name"));
                    brand.setSlug((String) row.get("brand_slug"));
                    brand.setDescription((String) row.get("brand_description"));
                    brand.setLogoUrl((String) row.get("brand_logo_url"));
                    brand.setWebsite((String) row.get("brand_website"));
                    product.setBrand(brand);
                }

                // 제품 상세 정보 설정
                if (row.get("product_detail_id") != null) {
                    ProductDetailDto detail = new ProductDetailDto();
                    detail.setId((Long) row.get("product_detail_id"));
                    detail.setProductId((Long) row.get("p_detail_product_id"));
                    detail.setWeight((BigDecimal) row.get("weight"));
                    detail.setDimensions((String) row.get("dimensions"));
                    detail.setMaterials((String) row.get("materials"));
                    detail.setCountryOfOrigin((String) row.get("country_of_origin"));
                    detail.setWarrantyInfo((String) row.get("warranty_info"));
                    detail.setCareInstructions((String) row.get("care_instructions"));
                    detail.setAdditionalInfo((String) row.get("additional_info"));
                    product.setDetail(detail);
                }

                // 제품 가격 정보 설정
                if (row.get("product_price_id") != null) {
                    ProductPriceDto price = new ProductPriceDto();
                    price.setId((Long) row.get("product_price_id"));
                    price.setProductId((Long) row.get("product_price_product_id"));
                    price.setBasePrice(convertToDouble(row.get("base_price")));
                    price.setCostPrice(convertToDouble(row.get("cost_price")));
                    price.setCurrency((String) row.get("currency"));
                    price.setTaxRate(convertToDouble(row.get("tax_rate")));
                    product.setPrice(price);
                }

                // 빈 리스트들 초기화
                product.setOptionGroups(new ArrayList<>());
                product.setImages(new ArrayList<>());
                product.setCategories(new ArrayList<>());
                product.setTags(new ArrayList<>());

                productsMap.put(productId, product);
            }

            // 옵션 그룹 및 옵션 처리
            if (row.get("option_group_id") != null) {
                Long optionGroupId = (Long) row.get("option_group_id");

                // 이미 처리한 옵션 그룹인지 확인
                ProductOptionsGroupDto optionGroup = product.getOptionGroups().stream()
                        .filter(og -> og.getId().equals(optionGroupId))
                        .findFirst()
                        .orElse(null);

                if (optionGroup == null) {
                    optionGroup = new ProductOptionsGroupDto();
                    optionGroup.setId(optionGroupId);
                    optionGroup.setProductId((Long) row.get("option_group_product_id"));
                    optionGroup.setName((String) row.get("option_group_name"));
                    optionGroup.setDisplayOrder((Integer) row.get("option_group_display_order"));
                    optionGroup.setOptions(new ArrayList<>());
                    product.getOptionGroups().add(optionGroup);
                }

                // 옵션 정보 처리
                if (row.get("option_id") != null) {
                    ProductOptionDto option = new ProductOptionDto();
                    option.setId((Long) row.get("option_id"));
                    option.setOptionGroupId((Long) row.get("option_option_group_id"));
                    option.setName((String) row.get("option_name"));
                    option.setAdditionalPrice(convertToDouble(row.get("additional_price")));
                    option.setSku((String) row.get("sku"));
                    option.setStock((Integer) row.get("stock"));
                    option.setDisplayOrder((Integer) row.get("option_display_order"));

                    // 이미 추가된 옵션인지 확인
                    boolean optionExists = optionGroup.getOptions().stream()
                            .anyMatch(o -> o.getId() == option.getId());

                    if (!optionExists) {
                        optionGroup.getOptions().add(option);
                    }
                }
            }

            // 이미지 정보 처리
            if (row.get("image_id") != null) {
                ProductImageDto image = new ProductImageDto();
                image.setId((Long) row.get("image_id"));
                image.setProductId((Long) row.get("image_product_id"));
                image.setUrl((String) row.get("image_url"));
                image.setAltText((String) row.get("alt_text"));
                image.setIsPrimary(convertToBoolean(row.get("is_primary")));
                image.setDisplayOrder((Integer) row.get("image_display_order"));
                image.setOptionId((Long) row.get("option_id"));

                // 이미 추가된 이미지인지 확인
                boolean imageExists = product.getImages().stream()
                        .anyMatch(i -> i.getId().equals(image.getId()));

                if (!imageExists) {
                    product.getImages().add(image);
                }
            }

            // 카테고리 정보 처리
            if (row.get("category_id") != null) {
                CategoryDto category = new CategoryDto();
                category.setId((Long) row.get("category_id"));
                category.setName((String) row.get("category_name"));
                category.setSlug((String) row.get("category_slug"));
                category.setDescription((String) row.get("category_description"));
                category.setParentId((Long) row.get("parent_id"));
                category.setLevel((Integer) row.get("level"));
                category.setImageUrl((String) row.get("category_image_url"));
                category.setIsPrimary(convertToBoolean(row.get("category_is_primary")));

                // 이미 추가된 카테고리인지 확인
                boolean categoryExists = product.getCategories().stream()
                        .anyMatch(c -> c.getId().equals(category.getId()));

                if (!categoryExists) {
                    product.getCategories().add(category);
                }
            }

            // 태그 정보 처리
            if (row.get("tag_id") != null) {
                TagDto tag = new TagDto();
                tag.setId((Long) row.get("tag_id"));
                tag.setName((String) row.get("tag_name"));
                tag.setSlug((String) row.get("tag_slug"));

                // 이미 추가된 태그인지 확인
                boolean tagExists = product.getTags().stream()
                        .anyMatch(t -> t.getId().equals(tag.getId()));

                if (!tagExists) {
                    product.getTags().add(tag);
                }
            }
        }

        return new ArrayList<>(productsMap.values());
    }

    // 유틸리티 메서드들
    private LocalDateTime convertToLocalDateTime(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) value).toLocalDateTime();
        } else if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        } else {
            throw new IllegalArgumentException("Cannot convert " + value.getClass().getName() + " to LocalDateTime");
        }
    }

    private Double convertToDouble(Object value) {
        if (value == null) return null;
        if (value instanceof Double) return (Double) value;
        if (value instanceof Integer) return ((Integer) value).doubleValue();
        if (value instanceof String) return Double.parseDouble((String) value);
        return null;
    }

    private Boolean convertToBoolean(Object value) {
        if (value == null) return null;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof Integer) return ((Integer) value) != 0;
        if (value instanceof String) return Boolean.parseBoolean((String) value);
        return null;
    }
}
