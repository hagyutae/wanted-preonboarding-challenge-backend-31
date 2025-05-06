package minseok.cqrschallenge.product.controller;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import minseok.cqrschallenge.common.dto.ApiResponse;
import minseok.cqrschallenge.product.dto.request.ProductCategoryRequest;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductImageCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductImageRequest;
import minseok.cqrschallenge.product.dto.request.ProductOptionCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductOptionGroupRequest;
import minseok.cqrschallenge.product.dto.request.ProductOptionRequest;
import minseok.cqrschallenge.product.dto.request.ProductPriceRequest;
import minseok.cqrschallenge.product.dto.request.ProductUpdateRequest;
import minseok.cqrschallenge.product.entity.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    private static final String BASE_URL = "/api/products";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long testProductId;

    @BeforeEach
    void setUp() throws Exception {
        // 각 테스트 전에 기본 상품 생성
        ProductCreateRequest request = createSampleProductRequest();

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        ApiResponse response = objectMapper.readValue(content, ApiResponse.class);
        Map<String, Object> data = (Map<String, Object>) response.getData();

        if (data != null && data.get("id") != null) {
            testProductId = Long.valueOf(data.get("id").toString());
        }
    }



    @Test
    @DisplayName("상품 목록 조회 - 기본 파라미터")
    public void getProducts_defaultParams_success() throws Exception {
        mockMvc.perform(get(BASE_URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.items").exists())
            .andExpect(jsonPath("$.data.pagination.current_page", is(1)))
            .andExpect(jsonPath("$.data.pagination.per_page", is(10)));
    }

    @Test
    @DisplayName("상품 목록 조회 - 페이징 및 정렬")
    public void getProducts_withPagingAndSorting_success() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .param("page", "1")
                .param("perPage", "5")
                .param("sort", "id:desc"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.pagination.current_page", is(1)))
            .andExpect(jsonPath("$.data.pagination.per_page", is(5)))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        ApiResponse response = objectMapper.readValue(content, ApiResponse.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) ((Map<String, Object>) response.getData()).get("items");
        if (items.size() >= 2) {
            int id1 = ((Number) items.get(0).get("id")).intValue();
            int id2 = ((Number) items.get(1).get("id")).intValue();
            assertTrue(id1 >= id2, "첫 번째 항목의 ID가 두 번째 항목의 ID보다 크거나 같아야 합니다(내림차순 정렬)");
        }
    }

    @Test
    @DisplayName("상품 목록 조회 - 상태 필터")
    public void getProducts_filterByStatus_success() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .param("status", "ACTIVE"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.items[*].status", everyItem(is("ACTIVE"))));
    }

    @Test
    @DisplayName("상품 목록 조회 - 가격 범위 필터")
    public void getProducts_filterByPriceRange_success() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .param("minPrice", "10000")
                .param("maxPrice", "1000000"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.items", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @DisplayName("상품 목록 조회 - 카테고리 필터")
    public void getProducts_filterByCategory_success() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .param("category", "5"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    @DisplayName("상품 목록 조회 - 복합 필터")
    public void getProducts_withMultipleFilters_success() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .param("status", "ACTIVE")
                .param("minPrice", "10000")
                .param("maxPrice", "1000000")
                .param("category", "5")
                .param("inStock", "true")
                .param("sort", "created_at:desc"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    @DisplayName("상품 상세 조회 - 성공")
    public void getProductDetail_success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testProductId))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.id", is(testProductId.intValue())))
            .andExpect(jsonPath("$.data.name").isString())
            .andExpect(jsonPath("$.data.slug").isString());
    }

    @Test
    @DisplayName("상품 상세 조회 - 존재하지 않는 상품")
    public void getProductDetail_notFound() throws Exception {
        long nonExistentProductId = 999999L;

        mockMvc.perform(get(BASE_URL + "/" + nonExistentProductId))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.error.code", is("RESOURCE_NOT_FOUND")));
    }

    @Test
    @DisplayName("상품 생성 - 성공")
    public void createProduct_success() throws Exception {
        ProductCreateRequest request = createSampleProductRequest();
        request.setSlug("another-unique-product-slug");

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.id").exists())
            .andExpect(jsonPath("$.data.name", is(request.getName())))
            .andExpect(jsonPath("$.data.slug", is(request.getSlug())))
            .andExpect(jsonPath("$.message", is("상품이 성공적으로 등록되었습니다.")));
    }

    @Test
    @DisplayName("상품 생성 - 유효성 검증 실패")
    public void createProduct_validationFailed() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        // 필수 필드 누락

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.error.code", is("INVALID_INPUT")))
            .andExpect(jsonPath("$.error.details").exists());
    }

    @Test
    @DisplayName("상품 업데이트 - 성공")
    public void updateProduct_success() throws Exception {
        ProductUpdateRequest request = ProductUpdateRequest.builder()
            .name("업데이트된 슈퍼 편안한 소파")
            .slug("updated-super-comfortable-sofa")
            .shortDescription("업데이트된 최고급 소재로 만든 편안한 소파")
            .fullDescription("<p>이 소파는 업데이트된 최고급 소재로 제작되었으며...</p>")
            .status(ProductStatus.ACTIVE.name())
            .build();

        mockMvc.perform(put(BASE_URL + "/" + testProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.id", is(testProductId.intValue())))
            .andExpect(jsonPath("$.data.name", is(request.getName())))
            .andExpect(jsonPath("$.data.slug", is(request.getSlug())))
            .andExpect(jsonPath("$.message", is("상품이 성공적으로 수정되었습니다.")));
    }

    @Test
    @DisplayName("상품 업데이트 - 존재하지 않는 상품")
    public void updateProduct_notFound() throws Exception {
        long nonExistentProductId = 999999L;

        ProductUpdateRequest request = ProductUpdateRequest.builder()
            .name("업데이트된 상품명")
            .slug("updated-product-slug")
            .status(ProductStatus.ACTIVE.name())
            .build();

        mockMvc.perform(put(BASE_URL + "/" + nonExistentProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.error.code", is("RESOURCE_NOT_FOUND")));
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    public void deleteProduct_success() throws Exception {


        mockMvc.perform(delete(BASE_URL + "/" + testProductId))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.message", is("상품이 성공적으로 삭제되었습니다.")));

        // 삭제 후 조회 시 404 확인
        mockMvc.perform(get(BASE_URL + "/" + testProductId))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("상품 삭제 - 존재하지 않는 상품")
    public void deleteProduct_notFound() throws Exception {
        long nonExistentProductId = 999999L;

        mockMvc.perform(delete(BASE_URL + "/" + nonExistentProductId))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.error.code", is("RESOURCE_NOT_FOUND")));
    }

    @Test
    @DisplayName("상품 옵션 추가 - 성공")
    public void addProductOption_success() throws Exception {
        // 옵션 추가 요청 객체 생성
        ProductOptionCreateRequest request = ProductOptionCreateRequest.builder()
            .optionGroupId(15L)
            .name("네이비")
            .additionalPrice(BigDecimal.valueOf(20000))
            .sku("SOFA-NVY")
            .stock(8)
            .displayOrder(3)
            .build();

        mockMvc.perform(post(BASE_URL + "/" + testProductId + "/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.id").exists())
            .andExpect(jsonPath("$.data.option_group_id", is(15)))
            .andExpect(jsonPath("$.data.name", is("네이비")))
            .andExpect(jsonPath("$.message", is("상품 옵션이 성공적으로 추가되었습니다.")));
    }

    @Test
    @DisplayName("상품 이미지 추가 - 성공")
    public void addProductImage_success() throws Exception {
        // 이미지 추가 요청 객체 생성
        ProductImageCreateRequest request = ProductImageCreateRequest.builder()
            .url("https://example.com/images/sofa3.jpg")
            .altText("네이비 소파 측면")
            .isPrimary(false)
            .displayOrder(3)
            .optionId(35L)
            .build();

        mockMvc.perform(post(BASE_URL + "/" + testProductId + "/images")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.id").exists())
            .andExpect(jsonPath("$.data.url", is("https://example.com/images/sofa3.jpg")))
            .andExpect(jsonPath("$.data.alt_text", is("네이비 소파 측면")))
            .andExpect(jsonPath("$.message", is("상품 이미지가 성공적으로 추가되었습니다.")));
    }

    /**
     * 테스트용 샘플 상품 생성 요청 객체를 생성합니다.
     */
    private ProductCreateRequest createSampleProductRequest() {
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
            .slug("super-comfortable-sofa-" + System.currentTimeMillis()) // 고유한 slug 생성
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