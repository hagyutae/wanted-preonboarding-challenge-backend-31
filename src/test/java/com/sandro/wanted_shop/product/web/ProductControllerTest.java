package com.sandro.wanted_shop.product.web;

import com.sandro.wanted_shop.common.util.ObjectMapperUtil;
import com.sandro.wanted_shop.config.IntegrationTestContext;
import com.sandro.wanted_shop.product.dto.*;
import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.product.entity.ProductImage;
import com.sandro.wanted_shop.product.entity.ProductOption;
import com.sandro.wanted_shop.product.entity.enums.ProductStatus;
import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends IntegrationTestContext {

    @DisplayName("상품을 등록한다.")
    @Test
    void registerProduct() throws Exception {
        long productsCount = productRepository.count();

        CreateProductCommand command = new CreateProductCommand(
                "마우스",
                "slug",
                "짧설명",
                "긴설명",
                1L,
                1L,
                ProductStatus.ACTIVE,
                new CreateProductCommand.Price(
                        BigDecimal.valueOf(1500),
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(800),
                        null,
                        BigDecimal.valueOf(10)
                ),
                new CreateProductCommand.Detail(
                        BigDecimal.valueOf(10),
                        new Dimensions(1f, 1f, 1f),
                        "플라스틱",
                        null,
                        null,
                        null,
                        null
                ),
                List.of(
                        new CreateProductCommand.Category(20L, true),
                        new CreateProductCommand.Category(42L, false)
                ),
                List.of(
                        new CreateOptionGroupCommand(
                                "타입",
                                1,
                                List.of(
                                        new CreateOptionCommand(
                                                null,
                                                "버티컬",
                                                BigDecimal.valueOf(1000),
                                                null,
                                                10,
                                                1
                                        ),
                                        new CreateOptionCommand(
                                                null,
                                                "기본",
                                                BigDecimal.ZERO,
                                                null,
                                                10,
                                                2
                                        )
                                )
                        ),
                        new CreateOptionGroupCommand(
                                "색상",
                                2,
                                List.of(
                                        new CreateOptionCommand(
                                                "화이트",
                                                BigDecimal.ZERO,
                                                null,
                                                10,
                                                1
                                        ),
                                        new CreateOptionCommand(
                                                "블랙",
                                                BigDecimal.ZERO,
                                                null,
                                                10,
                                                2
                                        )
                                )
                        )
                ),
                List.of(
                        new CreateImageCommand("url", null, true, 1),
                        new CreateImageCommand("url", null, false, 2)
                ),
                List.of(1L, 2L)
        );

        mvc
                .perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(ObjectMapperUtil.writeValueAsString(command))
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(productRepository.count()).isEqualTo(productsCount + 1);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Nested
    class GetAllProducts {
        @DisplayName("필터링 X, 정렬 X")
        @Test
        void noFilterNoSort() throws Exception {
            ResultActions resultActions = mvc
                    .perform(get("/api/products"))
                    .andDo(print());

            resultActions
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.content.[*].id").exists(),
                            jsonPath("$.pageable.pageSize").value(10)
                    )
                    .andDo(print());
        }

        @Nested
        class Sort {
            @DisplayName("최신순으로 정렬된다.")
            @Test
            void orderByCreatedAtDesc() throws Exception {
                ResultActions resultActions = mvc
                        .perform(
                                get("/api/products")
                                        .param("sort", "createdAt,desc")
                        )
                        .andDo(print());

                resultActions
                        .andExpectAll(
                                status().isOk(),
                                jsonPath("$.pageable.pageSize").value(10),
                                jsonPath("$.content.[*].id").exists(),
                                jsonPath("$.content.[0].name").value("공기청정기"),
                                jsonPath("$.content.[9].name").value("4K UHD 스마트 TV")
                        )
                        .andDo(print());
            }

            @DisplayName("평점 내림차순으로 정렬된다.")
            @Test
            void orderByRatingDesc() throws Exception {
                ResultActions resultActions = mvc
                        .perform(
                                get("/api/products")
                                        .param("sort", "reviews.rating,desc")
                        )
                        .andDo(print());

                resultActions
                        .andExpectAll(
                                status().isOk(),
                                jsonPath("$.pageable.pageSize").value(10),
                                jsonPath("$.content.[*].id").exists(),
                                jsonPath("$.content.[0].name").value("럭셔리 옷장"),
                                jsonPath("$.content.[9].name").value("패브릭 1인 소파")
                        )
                        .andDo(print());
            }

            @DisplayName("가격 내림차순으로 정렬된다.")
            @Test
            void orderByPriceDesc() throws Exception {
                ResultActions resultActions = mvc
                        .perform(
                                get("/api/products")
                                        .param("sort", "price.basePrice")
                        )
                        .andDo(print());

                resultActions
                        .andExpectAll(
                                status().isOk(),
                                jsonPath("$.pageable.pageSize").value(10),
                                jsonPath("$.content.[*].id").exists(),
                                jsonPath("$.content.[0].name").value("전문가용 수영 고글"),
                                jsonPath("$.content.[9].name").value("초고속 블렌더")
                        )
                        .andDo(print());
            }

            @DisplayName("평점 내림차순, 가격 내림차순, 최신순으로 정렬된다.")
            @Test
            void orderByComplexity() throws Exception {
                ResultActions resultActions = mvc
                        .perform(
                                get("/api/products")
                                        .param("sort", "reviews.rating,desc")
                                        .param("sort", "price.basePrice")
                                        .param("sort", "createdAt,desc")
                        )
                        .andDo(print());

                resultActions
                        .andExpectAll(
                                status().isOk(),
                                jsonPath("$.pageable.pageSize").value(10),
                                jsonPath("$.content.[*].id").exists(),
                                jsonPath("$.content.[0].name").value("프로페셔널 등산화"),
                                jsonPath("$.content.[9].name").value("편안한 침대 프레임")
                        )
                        .andDo(print());
            }
        }

        @DisplayName("정렬 X, 판매자로 필터링한다.")
        @Test
        void noSortFilteredBySellerId() throws Exception {
            ResultActions resultActions = mvc
                    .perform(
                            get("/api/products")
                                    .param("sellerId", "1")
                    )
                    .andDo(print());

            resultActions
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.content.[*].id").exists(),
                            jsonPath("$.content.size()").value(4)
                    )
                    .andDo(print());
        }

        @DisplayName("태그로 필터링한다.")
        @Test
        void noSortFilteredByTag() throws Exception {
            ResultActions resultActions = mvc
                    .perform(
                            get("/api/products")
                                    .param("tagId", "1")
                    )
                    .andDo(print());

            resultActions
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.content.[*].id").exists(),
                            jsonPath("$.content.size()").value(2)
                    )
                    .andDo(print());
        }

        @DisplayName("카테고리로 필터링한다.")
        @Test
        void noSortFilteredByCategory() throws Exception {
            ResultActions resultActions = mvc
                    .perform(
                            get("/api/products")
                                    .param("categoryId", "2")
                    )
                    .andDo(print());

            resultActions
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.content.[*].id").exists(),
                            jsonPath("$.content.size()").value(1)
                    )
                    .andDo(print());
        }

        @DisplayName("검색어로 필터링한다.")
        @Test
        void noSortFilteredByKeyword() throws Exception {
            ResultActions resultActions = mvc
                    .perform(
                            get("/api/products")
                                    .param("keyword", "공기청정기")
                    )
                    .andDo(print());

            resultActions
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.content.[*].id").exists(),
                            jsonPath("$.content.size()").value(1)
                    )
                    .andDo(print());
        }

        @DisplayName("재고 유무로 필터링한다.")
        @Test
        void noSortFilteredByhasStock() throws Exception {
            productService.register(new CreateProductCommand(
                    "재고없는 제품",
                    "slug",
                    null,
                    null,
                    1L,
                    1L,
                    ProductStatus.ACTIVE,
                    new CreateProductCommand.Price(),
                    new CreateProductCommand.Detail(),
                    List.of(new CreateProductCommand.Category(1L, true)),
                    List.of(new CreateOptionGroupCommand(
                            "옵션 그룹",
                            1,
                            List.of(
                                    new CreateOptionCommand(
                                            "옵션",
                                            null,
                                            null,
                                            0,
                                            null
                                    )
                            )
                    )),
                    null,
                    List.of(1L)
            ));

            ResultActions resultActions = mvc
                    .perform(
                            get("/api/products")
                                    .param("hasStock", "false")
                    )
                    .andDo(print());

            resultActions
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.content.[*].id").exists(),
                            jsonPath("$.content.size()").value(1)
                    )
                    .andDo(print());
        }
    }

    @DisplayName("상품 상세 조회")
    @Test
    void getProductDetail() throws Exception {
        long productId = 1L;

        ResultActions resultActions = mvc
                .perform(get("/api/products/{id}", productId));

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.name").exists(),
                        jsonPath("$.price").exists(),
                        jsonPath("$.detail").exists(),
                        jsonPath("$.categories").exists(),
                        jsonPath("$.optionGroups").exists(),
                        jsonPath("$.images").exists(),
                        jsonPath("$.tags").exists(),
                        jsonPath("$.reviews").exists()
                )
                .andDo(print());
    }

    // TODO: 고도화하기
    @DisplayName("상품을 수정한다.")
    @Test
    void updateProduct() throws Exception {
        long productId = 1L;

        Product product = productRepository.findById(productId).orElseThrow();

        UpdateProductCommand command = new UpdateProductCommand(
                "수정된 이름",
                product.getSlug(),
                product.getShortDescription(),
                product.getFullDescription(),
                product.getStatus()
        );

        mvc
                .perform(
                        put("/api/products/{id}", productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(ObjectMapperUtil.writeValueAsString(command))
                )
                .andExpect(status().isOk())
                .andDo(print());

        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        assertThat(updatedProduct.getName()).isEqualTo("수정된 이름");
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteProduct() throws Exception {
        long productId = 1L;

        assertThat(productRepository.findById(productId)).isPresent();

        mvc
                .perform(delete("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(productRepository.findById(productId)).isEmpty();
    }

    @DisplayName("옵션을 추가한다.")
    @Test
    void addOption() throws Exception {
        long productId = 1L;
        long optionGroupId = 1L;

        CreateOptionCommand command = new CreateOptionCommand(
                optionGroupId,
                "그린",
                BigDecimal.valueOf(0.99),
                "SOFA-GRN",
                10,
                3
        );

        mvc
                .perform(
                        post("/api/products/{id}/options", productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(ObjectMapperUtil.writeValueAsString(command))
                )
                .andExpect(status().isOk())
                .andDo(print());


        ProductOptionGroup optionGroup = optionGroupRepository
                .findWithOptionsByProduct_idAndId(optionGroupId, productId).orElseThrow();
        List<ProductOption> options = optionGroup.getOptions();
        assertThat(options.size()).isEqualTo(3);
        assertThat(options.getLast().getName()).isEqualTo("그린");
    }

    @DisplayName("옵션을 수정한다.")
    @Test
    void updateOption() throws Exception {
        long productId = 1L;
        long optionId = 1L;

        ProductOption option = optionRepository.findById(optionId).orElseThrow();
        assertThat(option.getName()).isEqualTo("브라운");

        UpdateOptionCommand command = new UpdateOptionCommand(
                "갈색",
                option.getAdditionalPrice(),
                option.getSku(),
                option.getStock() + 1,
                option.getDisplayOrder()
        );

        mvc
                .perform(
                        put("/api/products/{id}/options/{optionId}", productId, optionId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(ObjectMapperUtil.writeValueAsString(command))
                )
                .andExpect(status().isOk())
                .andDo(print());

        ProductOption updatedOption = optionRepository.findById(optionId).orElseThrow();
        assertThat(updatedOption.getName()).isEqualTo("갈색");
        assertThat(updatedOption.getStock()).isEqualTo(11);
    }

    @DisplayName("옵션을 삭제한다.")
    @Test
    void deleteOption() throws Exception {
        long productId = 1L;
        long optionId = 1L;

        assertThat(optionRepository.findById(optionId)).isPresent();

        mvc
                .perform(delete("/api/products/{id}/options/{optionId}", productId, optionId))
                .andDo(print());

        assertThat(optionRepository.findById(optionId)).isEmpty();
    }

    @DisplayName("이미지를 등록한다.")
    @Test
    void addImage() throws Exception {
        long productId = 1L;

        CreateImageCommand command = new CreateImageCommand(
                "url",
                "설명",
                false,
                5
        );

        mvc
                .perform(
                        post("/api/products/{id}/images", productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(ObjectMapperUtil.writeValueAsString(List.of(command)))
                )
                .andExpect(status().isOk())
                .andDo(print());

        Product foundProduct = productRepository.findWithImagesById(productId).orElseThrow();
        List<ProductImage> images = foundProduct.getImages();
        assertThat(images.size()).isEqualTo(5);
        assertThat(images.getLast().getUrl()).isEqualTo("url");
    }
}