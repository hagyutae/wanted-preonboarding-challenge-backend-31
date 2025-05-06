package minseok.cqrschallenge.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import minseok.cqrschallenge.product.dto.request.ProductCategoryRequest;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductImageRequest;
import minseok.cqrschallenge.product.dto.request.ProductOptionGroupRequest;
import minseok.cqrschallenge.product.dto.request.ProductOptionRequest;
import minseok.cqrschallenge.product.dto.request.ProductPriceRequest;
import minseok.cqrschallenge.product.entity.ProductStatus;

public class TestUtils {

    public static final ObjectMapper objectMapper = new ObjectMapper();


    public static ProductCreateRequest createSampleProductRequest() {
        ProductPriceRequest priceRequest = ProductPriceRequest.builder()
            .basePrice(BigDecimal.valueOf(599000))
            .salePrice(BigDecimal.valueOf(499000))
            .costPrice(BigDecimal.valueOf(350000))
            .currency("KRW")
            .taxRate(BigDecimal.valueOf(10))
            .build();

        List<ProductCategoryRequest> categories = Arrays.asList(
            ProductCategoryRequest.builder()
                .categoryId(5L)
                .isPrimary(true)
                .build(),
            ProductCategoryRequest.builder()
                .categoryId(8L)
                .isPrimary(false)
                .build()
        );

        List<ProductOptionGroupRequest> optionGroups = Arrays.asList(
            ProductOptionGroupRequest.builder()
                .name("색상")
                .displayOrder(1)
                .options(Arrays.asList(
                    ProductOptionRequest.builder()
                        .name("브라운")
                        .additionalPrice(BigDecimal.ZERO)
                        .sku("SOFA-BRN")
                        .stock(10)
                        .displayOrder(1)
                        .build(),
                    ProductOptionRequest.builder()
                        .name("블랙")
                        .additionalPrice(BigDecimal.ZERO)
                        .sku("SOFA-BLK")
                        .stock(15)
                        .displayOrder(2)
                        .build()
                ))
                .build(),

            ProductOptionGroupRequest.builder()
                .name("소재")
                .displayOrder(2)
                .options(Arrays.asList(
                    ProductOptionRequest.builder()
                        .name("천연 가죽")
                        .additionalPrice(BigDecimal.valueOf(100000))
                        .sku("SOFA-LTHR")
                        .stock(5)
                        .displayOrder(1)
                        .build(),
                    ProductOptionRequest.builder()
                        .name("인조 가죽")
                        .additionalPrice(BigDecimal.ZERO)
                        .sku("SOFA-FAKE")
                        .stock(20)
                        .displayOrder(2)
                        .build()
                ))
                .build()
        );

        List<ProductImageRequest> images = Arrays.asList(
            ProductImageRequest.builder()
                .url("https://example.com/images/sofa1.jpg")
                .altText("브라운 소파 정면")
                .isPrimary(true)
                .displayOrder(1)
                .build(),
            ProductImageRequest.builder()
                .url("https://example.com/images/sofa2.jpg")
                .altText("브라운 소파 측면")
                .isPrimary(false)
                .displayOrder(2)
                .build()
        );

        return ProductCreateRequest.builder()
            .name("슈퍼 편안한 소파")
            .slug("super-comfortable-sofa")
            .shortDescription("최고급 소재로 만든 편안한 소파")
            .fullDescription("<p>이 소파는 최고급 소재로 제작되었으며...</p>")
            .status(ProductStatus.ACTIVE.name())
            .sellerId(1L)
            .brandId(2L)
            .price(priceRequest)
            .categories(categories)
            .optionGroups(optionGroups)
            .images(images)
            .tags(Arrays.asList(1L, 4L, 7L))
            .build();
    }
}