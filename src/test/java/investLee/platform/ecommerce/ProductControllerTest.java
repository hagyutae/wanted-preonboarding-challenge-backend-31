package investLee.platform.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import investLee.platform.ecommerce.domain.ProductStatus;
import investLee.platform.ecommerce.domain.entity.BrandEntity;
import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ProductPriceEntity;
import investLee.platform.ecommerce.domain.entity.SellerEntity;
import investLee.platform.ecommerce.dto.request.ProductCreateRequest;
import investLee.platform.ecommerce.repository.BrandRepository;
import investLee.platform.ecommerce.repository.ProductPriceRepository;
import investLee.platform.ecommerce.repository.ProductRepository;
import investLee.platform.ecommerce.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Test
    void 상품_등록_요청_성공() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("테스트 상품")
                .slug("test-product")
                .shortDescription("짧은 설명")
                .fullDescription("긴 설명입니다")
                .brandId(1L)
                .sellerId(1L)
                .status(ProductStatus.AVAILABLE)
                .price(ProductCreateRequest.ProductPriceDTO.builder()
                        .basePrice(BigDecimal.valueOf(10000))
                        .salePrice(BigDecimal.valueOf(8000))
                        .costPrice(BigDecimal.valueOf(6000))
                        .currency("KRW")
                        .taxRate(BigDecimal.valueOf(10))
                        .build())
                .build();

        // when & then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
