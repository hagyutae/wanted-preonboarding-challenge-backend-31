package com.ecommerce.product.application;

import com.ecommerce.brand.domain.Brand;
import com.ecommerce.brand.infrastructure.BrandRepository;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.response.ErrorCode;
import com.ecommerce.product.application.dto.req.ProductCreateRequest;
import com.ecommerce.product.application.dto.res.ProductCreatedResponse;
import com.ecommerce.product.domain.AdditionalInfoVO;
import com.ecommerce.product.domain.DimensionsVO;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductDetail;
import com.ecommerce.product.domain.ProductImage;
import com.ecommerce.product.domain.ProductOption;
import com.ecommerce.product.domain.ProductOptionGroup;
import com.ecommerce.product.domain.ProductPrice;
import com.ecommerce.product.domain.ProductTag;
import com.ecommerce.product.domain.enumerates.ProductStatus;
import com.ecommerce.product.infrastructure.ProductDetailRepository;
import com.ecommerce.product.infrastructure.ProductImageRepository;
import com.ecommerce.product.infrastructure.ProductOptionGroupRepository;
import com.ecommerce.product.infrastructure.ProductOptionRepository;
import com.ecommerce.product.infrastructure.ProductPriceRepository;
import com.ecommerce.product.infrastructure.ProductRepository;
import com.ecommerce.product.infrastructure.ProductTagRepository;
import com.ecommerce.seller.domain.Seller;
import com.ecommerce.seller.infrastructure.SellerRepository;
import com.ecommerce.tag.domain.Tag;
import com.ecommerce.tag.infrastructure.TagRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductDetailRepository productDetailRepository;

    @Mock
    private ProductTagRepository productTagRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProductPriceRepository productPriceRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @Mock
    private ProductOptionGroupRepository productOptionGroupRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Seller seller;
    private Brand brand;
    private Product product;
    private ProductCreateRequest productCreateRequest;
    private List<Tag> tags;

    @BeforeEach
    void setUp() {
        seller = Seller.builder()
                .id(1L)
                .name("테스트 판매자")
                .build();

        brand = Brand.builder()
                .id(1L)
                .name("테스트 브랜드")
                .build();

        product = Product.builder()
                .id(1L)
                .name("테스트 상품")
                .slug("test-product")
                .shortDescription("짧은 설명")
                .fullDescription("긴 설명")
                .seller(seller)
                .brand(brand)
                .status(ProductStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        tags = Arrays.asList(
                Tag.builder().id(1L).name("태그1").build(),
                Tag.builder().id(2L).name("태그2").build()
        );

        productCreateRequest = createProductCreateRequest();
    }

    @Test
    @DisplayName("상품 생성 성공 테스트")
    void createProductSuccess() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brand));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(tagRepository.findByIds(anyList())).thenReturn(tags);

        when(productDetailRepository.save(any(ProductDetail.class))).thenReturn(
                ProductDetail.builder().id(1L).product(product).build()
        );
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(
                ProductPrice.builder().id(1L).product(product).build()
        );
        when(productTagRepository.save(any(ProductTag.class))).thenReturn(
                ProductTag.builder().id(1L).product(product).tag(tags.get(0)).build()
        );

        ProductOptionGroup optionGroup = ProductOptionGroup.builder()
                .id(1L)
                .product(product)
                .name("사이즈")
                .displayOrder(1)
                .build();

        when(productOptionGroupRepository.save(any(ProductOptionGroup.class))).thenReturn(optionGroup);

        when(productOptionRepository.save(any(ProductOption.class))).thenReturn(
                ProductOption.builder()
                        .id(1L)
                        .optionGroup(optionGroup)
                        .name("S")
                        .sku("SKU-001-S")
                        .stock(100)
                        .build()
        );

        when(productImageRepository.save(any(ProductImage.class))).thenReturn(
                ProductImage.builder()
                        .id(1L)
                        .product(product)
                        .url("http://example.com/image1.jpg")
                        .isPrimary(true)
                        .build()
        );

        // When
        ProductCreatedResponse response = productService.create(productCreateRequest);

        // Then
        assertThat(response).isNotNull();
        assertEquals(1L, response.id());
        assertEquals("테스트 상품", response.name());
        assertEquals("test-product", response.slug());

        verify(sellerRepository).findById(1L);
        verify(brandRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
        verify(productDetailRepository).save(any(ProductDetail.class));
        verify(productPriceRepository).save(any(ProductPrice.class));

        verify(tagRepository).findByIds(Arrays.asList(1L, 2L));
        verify(productTagRepository, times(2)).save(any(ProductTag.class));

        verify(productOptionGroupRepository).save(any(ProductOptionGroup.class));
        verify(productOptionRepository, times(3)).save(any(ProductOption.class));

        verify(productImageRepository, times(2)).save(any(ProductImage.class));
    }

    @Test
    @DisplayName("판매자가 존재하지 않을 때 예외 발생 테스트")
    void createProductWithNonExistingSeller() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.create(productCreateRequest));

        assertEquals(ErrorCode.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());

        // 예외 발생 후 다른 메서드 호출 여부 검증
        verify(sellerRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
        verify(productDetailRepository, never()).save(any(ProductDetail.class));
    }

    @Test
    @DisplayName("브랜드가 존재하지 않을 때 예외 발생 테스트")
    void createProductWithNonExistingBrand() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.create(productCreateRequest));

        assertEquals(ErrorCode.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());

        verify(sellerRepository).findById(1L);
        verify(brandRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 저장 중 예외 발생 테스트")
    void createProductRepositoryException() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brand));
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("데이터베이스 저장 오류"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.create(productCreateRequest));

        assertEquals("데이터베이스 저장 오류", exception.getMessage());

        verify(sellerRepository).findById(1L);
        verify(brandRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
        verify(productDetailRepository, never()).save(any(ProductDetail.class));
    }

    @Test
    @DisplayName("태그가 존재하지 않을 때 빈 리스트 반환 테스트")
    void createProductWithNonExistingTags() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brand));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(tagRepository.findByIds(anyList())).thenReturn(new ArrayList<>()); // 빈 태그 리스트 반환

        when(productDetailRepository.save(any(ProductDetail.class))).thenReturn(
                ProductDetail.builder().id(1L).product(product).build()
        );
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(
                ProductPrice.builder().id(1L).product(product).build()
        );
        when(productOptionGroupRepository.save(any(ProductOptionGroup.class))).thenReturn(
                ProductOptionGroup.builder().id(1L).product(product).build()
        );
        when(productOptionRepository.save(any(ProductOption.class))).thenReturn(
                ProductOption.builder().id(1L).build()
        );
        when(productImageRepository.save(any(ProductImage.class))).thenReturn(
                ProductImage.builder().id(1L).product(product).build()
        );

        // When
        ProductCreatedResponse response = productService.create(productCreateRequest);

        // Then
        assertThat(response).isNotNull();
        verify(tagRepository).findByIds(anyList());
        verify(productTagRepository, never()).save(any(ProductTag.class));
    }

    @Test
    @DisplayName("DimensionsVO 변환 테스트")
    void testDimensionsVOConversion() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brand));

        Product savedProduct = Product.builder()
                .id(1L)
                .name("테스트 상품")
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ArgumentCaptor<ProductDetail> productDetailCaptor = ArgumentCaptor.forClass(ProductDetail.class);
        when(productDetailRepository.save(productDetailCaptor.capture())).thenReturn(
                ProductDetail.builder().id(1L).product(savedProduct).build()
        );

        when(tagRepository.findByIds(anyList())).thenReturn(tags);
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(
                ProductPrice.builder().id(1L).product(savedProduct).build()
        );
        when(productTagRepository.save(any(ProductTag.class))).thenReturn(
                ProductTag.builder().id(1L).product(savedProduct).tag(tags.get(0)).build()
        );
        when(productOptionGroupRepository.save(any(ProductOptionGroup.class))).thenReturn(
                ProductOptionGroup.builder().id(1L).product(savedProduct).build()
        );
        when(productOptionRepository.save(any(ProductOption.class))).thenReturn(
                ProductOption.builder().id(1L).build()
        );
        when(productImageRepository.save(any(ProductImage.class))).thenReturn(
                ProductImage.builder().id(1L).product(savedProduct).build()
        );

        // When
        productService.create(productCreateRequest);

        // Then
        ProductDetail capturedProductDetail = productDetailCaptor.getValue();
        DimensionsVO dimensions = capturedProductDetail.getDimensions();

        assertEquals(100, dimensions.width());
        assertEquals(150, dimensions.height());
        assertEquals(50, dimensions.depth());
    }

    @Test
    @DisplayName("AdditionalInfoVO 변환 테스트")
    void testAdditionalInfoVOConversion() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brand));

        Product savedProduct = Product.builder()
                .id(1L)
                .name("테스트 상품")
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ArgumentCaptor<ProductDetail> productDetailCaptor = ArgumentCaptor.forClass(ProductDetail.class);
        when(productDetailRepository.save(productDetailCaptor.capture())).thenReturn(
                ProductDetail.builder().id(1L).product(savedProduct).build()
        );

        when(tagRepository.findByIds(anyList())).thenReturn(tags);
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(
                ProductPrice.builder().id(1L).product(savedProduct).build()
        );
        when(productTagRepository.save(any(ProductTag.class))).thenReturn(
                ProductTag.builder().id(1L).product(savedProduct).tag(tags.get(0)).build()
        );
        when(productOptionGroupRepository.save(any(ProductOptionGroup.class))).thenReturn(
                ProductOptionGroup.builder().id(1L).product(savedProduct).build()
        );
        when(productOptionRepository.save(any(ProductOption.class))).thenReturn(
                ProductOption.builder().id(1L).build()
        );
        when(productImageRepository.save(any(ProductImage.class))).thenReturn(
                ProductImage.builder().id(1L).product(savedProduct).build()
        );

        // When
        productService.create(productCreateRequest);

        // Then
        ProductDetail capturedProductDetail = productDetailCaptor.getValue();
        AdditionalInfoVO additionalInfo = capturedProductDetail.getAdditionalInfo();

        assertEquals(true, additionalInfo.isAssemblyRequired());
        assertEquals("30분", additionalInfo.getAssemblyTime());
    }

    @Test
    @DisplayName("ProductStatus 열거형 변환 테스트")
    void testProductStatusEnumConversion() {
        // Given
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brand));

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        when(productRepository.save(productCaptor.capture())).thenReturn(product);

        when(tagRepository.findByIds(anyList())).thenReturn(tags);
        when(productDetailRepository.save(any(ProductDetail.class))).thenReturn(
                ProductDetail.builder().id(1L).product(product).build()
        );
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(
                ProductPrice.builder().id(1L).product(product).build()
        );
        when(productTagRepository.save(any(ProductTag.class))).thenReturn(
                ProductTag.builder().id(1L).product(product).tag(tags.get(0)).build()
        );
        when(productOptionGroupRepository.save(any(ProductOptionGroup.class))).thenReturn(
                ProductOptionGroup.builder().id(1L).product(product).build()
        );
        when(productOptionRepository.save(any(ProductOption.class))).thenReturn(
                ProductOption.builder().id(1L).build()
        );
        when(productImageRepository.save(any(ProductImage.class))).thenReturn(
                ProductImage.builder().id(1L).product(product).url("test").displayOrder(10).build()
        );

        ProductCreateRequest draftProductRequest = new ProductCreateRequest(
                productCreateRequest.name(),
                productCreateRequest.slug(),
                productCreateRequest.shortDescription(),
                productCreateRequest.fullDescription(),
                productCreateRequest.sellerId(),
                productCreateRequest.brandId(),
                "OUT_OF_STOCK",
                productCreateRequest.detail(),
                productCreateRequest.price(),
                productCreateRequest.categories(),
                productCreateRequest.optionGroups(),
                productCreateRequest.images(),
                productCreateRequest.tagIds()
        );

        // When
        productService.create(draftProductRequest);

        // Then
        Product capturedProduct = productCaptor.getValue();
        assertEquals(ProductStatus.OUT_OF_STOCK, capturedProduct.getStatus());
    }

    @Test
    @DisplayName("옵션이 없는 상품 생성 테스트")
    void createProductWithoutOptions() {
        // Given
        // 옵션 없는 상품 요청 생성
        ProductCreateRequest requestWithoutOptions = new ProductCreateRequest(
                productCreateRequest.name(),
                productCreateRequest.slug(),
                productCreateRequest.shortDescription(),
                productCreateRequest.fullDescription(),
                productCreateRequest.sellerId(),
                productCreateRequest.brandId(),
                productCreateRequest.status(),
                productCreateRequest.detail(),
                productCreateRequest.price(),
                productCreateRequest.categories(),
                new ArrayList<>(),
                productCreateRequest.images(),
                productCreateRequest.tagIds()
        );

        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brand));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(tagRepository.findByIds(anyList())).thenReturn(tags);

        when(productDetailRepository.save(any(ProductDetail.class))).thenReturn(
                ProductDetail.builder().id(1L).product(product).build()
        );
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(
                ProductPrice.builder().id(1L).product(product).build()
        );
        when(productTagRepository.save(any(ProductTag.class))).thenReturn(
                ProductTag.builder().id(1L).product(product).tag(tags.get(0)).build()
        );
        when(productImageRepository.save(any(ProductImage.class))).thenReturn(
                ProductImage.builder().id(1L).product(product).build()
        );

        // When
        ProductCreatedResponse response = productService.create(requestWithoutOptions);

        // Then
        assertThat(response).isNotNull();
        verify(productOptionGroupRepository, never()).save(any(ProductOptionGroup.class));
        verify(productOptionRepository, never()).save(any(ProductOption.class));
    }

    private ProductCreateRequest createProductCreateRequest() {
        ProductCreateRequest.ProductDetailRequest.DimensionsRequest dimensionsRequest =
                new ProductCreateRequest.ProductDetailRequest.DimensionsRequest(
                        100,
                        150,
                        50
                );

        ProductCreateRequest.ProductDetailRequest.AdditionalInfoRequest additionalInfoRequest =
                new ProductCreateRequest.ProductDetailRequest.AdditionalInfoRequest(
                        true,
                        "30분"
                );

        ProductCreateRequest.ProductDetailRequest detailRequest =
                new ProductCreateRequest.ProductDetailRequest(
                        1.5,
                        dimensionsRequest,
                        "면 100%",
                        "대한민국",
                        "1년 무상 보증",
                        "물세탁 금지",
                        additionalInfoRequest
                );

        ProductCreateRequest.ProductPriceRequest priceRequest =
                new ProductCreateRequest.ProductPriceRequest(
                        new BigDecimal("50000"),
                        new BigDecimal("45000"),
                        new BigDecimal("30000"),
                        "KRW",
                        new BigDecimal("0.1")
                );


        List<ProductCreateRequest.ProductCategoryRequest> categoryRequests = new ArrayList<>();
        categoryRequests.add(new ProductCreateRequest.ProductCategoryRequest(
                101L,
                true
        ));
        categoryRequests.add(new ProductCreateRequest.ProductCategoryRequest(
                102L,
                false
        ));


        List<ProductCreateRequest.ProductOptionGroupRequest.ProductOptionRequest> optionRequests = new ArrayList<>();
        optionRequests.add(new ProductCreateRequest.ProductOptionGroupRequest.ProductOptionRequest(
                "S",
                new BigDecimal("0"),
                "SKU-001-S",
                100,
                1
        ));
        optionRequests.add(new ProductCreateRequest.ProductOptionGroupRequest.ProductOptionRequest(
                "M",
                new BigDecimal("2000"),
                "SKU-001-M",
                150,
                2
        ));
        optionRequests.add(new ProductCreateRequest.ProductOptionGroupRequest.ProductOptionRequest(
                "L",
                new BigDecimal("5000"),
                "SKU-001-L",
                80,
                3
        ));

        List<ProductCreateRequest.ProductOptionGroupRequest> optionGroupRequests = new ArrayList<>();
        optionGroupRequests.add(new ProductCreateRequest.ProductOptionGroupRequest(
                "사이즈",
                1,
                optionRequests
        ));


        List<ProductCreateRequest.ProductImageRequest> imageRequests = new ArrayList<>();
        imageRequests.add(new ProductCreateRequest.ProductImageRequest(
                "http://example.com/image1.jpg",
                "메인 상품 이미지",
                true,
                1,
                null
        ));
        imageRequests.add(new ProductCreateRequest.ProductImageRequest(
                "http://example.com/image2.jpg",
                "상품 이미지 측면",
                false,
                2,
                null
        ));

        List<Long> tagIds = Arrays.asList(1L, 2L);

        return new ProductCreateRequest(
                "테스트 상품",
                "test-product",
                "짧은 설명",
                "긴 설명",
                1L,
                1L,
                "ACTIVE",
                detailRequest,
                priceRequest,
                categoryRequests,
                optionGroupRequests,
                imageRequests,
                tagIds
        );
    }
}
