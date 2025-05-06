package com.wanted.ecommerce.product.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.brand.dto.response.BrandResponse;
import com.wanted.ecommerce.brand.service.BrandService;
import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.product.domain.Dimensions;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductCategory;
import com.wanted.ecommerce.product.domain.ProductDetail;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductPrice;
import com.wanted.ecommerce.product.domain.ProductStatus;
import com.wanted.ecommerce.product.domain.ProductTag;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.DimensionsRequest;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductPriceRequest;
import com.wanted.ecommerce.product.dto.request.ProductSearchRequest;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductListResponse;
import com.wanted.ecommerce.product.dto.response.ProductRegisterResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse.DetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse.ProductPriceResponse;
import com.wanted.ecommerce.product.dto.response.ProductUpdateResponse;
import com.wanted.ecommerce.product.repository.ProductRepository;
import com.wanted.ecommerce.product.service.ProductCategoryService;
import com.wanted.ecommerce.product.service.ProductDetailService;
import com.wanted.ecommerce.product.service.ProductImageServiceFacade;
import com.wanted.ecommerce.product.service.ProductOptionGroupService;
import com.wanted.ecommerce.product.service.ProductOptionService;
import com.wanted.ecommerce.product.service.ProductPriceService;
import com.wanted.ecommerce.product.service.ProductTagService;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import com.wanted.ecommerce.review.service.ReviewService;
import com.wanted.ecommerce.seller.domain.Seller;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.seller.dto.response.SellerResponse;
import com.wanted.ecommerce.seller.service.SellerService;
import com.wanted.ecommerce.tag.service.TagService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCategoryService productCategoryService;
    @Mock
    private ProductDetailService productDetailService;
    @Mock
    private ProductOptionGroupService productOptionGroupService;
    @Mock
    private ProductOptionService productOptionService;
    @Mock
    private ProductPriceService productPriceService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private ProductTagService productTagService;
    @Mock
    private TagService tagService;
    @Mock
    private SellerService sellerService;
    @Mock
    private BrandService brandService;
    @Mock
    private ProductImageServiceFacade productImageServiceFacade;

    private Product product;
    private Seller seller;
    private Brand brand;
    private ProductPrice price;
    private ProductDetail detail;
    private List<ProductCategory> categories;
    private List<ProductImage> images;
    private List<ProductTag> tags;

    @BeforeEach
    void setUp() {
        seller = registProductSeller();
        brand = registProductBrand();
        price = registProductPrice();
        detail = registProductDetail();
        categories = registProductCategories();
        images = registProductImages();
        tags = registProductTags();
        product = registProductProduct();
    }

    @Test
    void test_registProduct_success() {
        ProductRegisterRequest request = mock(ProductRegisterRequest.class);
        when(request.getSellerId()).thenReturn(1L);
        when(request.getBrandId()).thenReturn(1L);
        when(request.getName()).thenReturn("Product");
        when(request.getSlug()).thenReturn("product-slug");
        when(request.getShortDescription()).thenReturn("short");
        when(request.getFullDescription()).thenReturn("full");
        when(request.getStatus()).thenReturn("ACTIVE");

        when(sellerService.getSellerById(1L)).thenReturn(seller);
        when(brandService.getBrandById(1L)).thenReturn(brand);
        when(productRepository.save(any())).thenReturn(product);

        ProductRegisterResponse response = productService.registProduct(request);

        assertNotNull(response);
        assertEquals("Product", response.name());
    }

    @Test
    void test_readAll_success() {
        ProductSearchRequest request = new ProductSearchRequest("ACTIVE", 1000, 10000000, "1", 1, 1,
            true, "TEST");
        Pageable pageable = PageRequest.of(0, 10); // 페이지 번호 0, 페이지 크기 10

        PageImpl<Product> productPage = new PageImpl<>(List.of(product));

        when(productRepository.findAllByRequest(any(), any())).thenReturn(
            productPage);
        when(productImageServiceFacade.getPrimaryProductImageResponse(anyLong())).thenReturn(mock(
            ProductImageResponse.class));
        when(reviewService.getAvgRatingByProductId(anyLong())).thenReturn(4.123);
        when(reviewService.getReviewCountByProductId(anyLong())).thenReturn(10);
        when(productOptionService.isExistStock(anyLong(), anyInt())).thenReturn(true);
        when(brandService.createBrandResponse(any())).thenReturn(
            mock(BrandResponse.class));
        when(sellerService.createSellerResponse(any())).thenReturn(
            mock(SellerResponse.class));

        Page<ProductListResponse> result = productService.readAll(request, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void test_readDetail_success() {
        Product relateProduct = registProductReleateProduct();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(brandService.createBrandDetailResponse(any())).thenReturn(
            mock(BrandDetailResponse.class));
        when(sellerService.createSellerDetailResponse(any())).thenReturn(
            mock(SellerDetailResponse.class));
        when(productDetailService.createProductDetailResponse(any())).thenReturn(mock(
            DetailResponse.class));
        when(productPriceService.createPriceResponse(any())).thenReturn(
            mock(ProductPriceResponse.class));
        when(productCategoryService.createCategoryResponse(any())).thenReturn(List.of());
        when(productOptionGroupService.createOptionGroupResponse(any())).thenReturn(List.of());
        when(productImageServiceFacade.getImageResponse(any())).thenReturn(List.of());
        when(reviewService.createRatingResponse(anyLong())).thenReturn(mock(RatingResponse.class));
        when(productRepository.findRelatedProductsByCategoryId(any())).thenReturn(
            List.of(relateProduct));

        ProductResponse response = productService.readDetail(1L);
        assertNotNull(response);
    }

    @Test
    void test_update_Product_success() {
        ProductRegisterRequest request = mock(ProductRegisterRequest.class);

        when(request.getSellerId()).thenReturn(1L);
        when(request.getBrandId()).thenReturn(1L);
        when(request.getName()).thenReturn("Updated");
        when(request.getSlug()).thenReturn("updated-slug");
        when(request.getShortDescription()).thenReturn("short");
        when(request.getFullDescription()).thenReturn("full");
        when(request.getStatus()).thenReturn("ACTIVE");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(sellerService.getSellerById(1L)).thenReturn(seller);
        when(brandService.getBrandById(1L)).thenReturn(brand);

        ProductUpdateResponse response = productService.updateProduct(1L, request);

        assertNotNull(response);
        assertEquals("Updated", response.name());
    }

    @Test
    void test_delete_Product_success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        productService.deleteProduct(1L);
        verify(productRepository).delete(product);
    }

    @Test
    void test_getProductById_success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Product result = productService.getProductById(1L);
        assertNotNull(result);
        assertEquals(result.getId(), product.getId());
    }

    private Seller registProductSeller() {
        return Seller.builder()
            .id(1L)
            .name("TestSeller")
            .description("description")
            .logoUrl("https://example.com/logos/homefurniture.png")
            .rating(new BigDecimal("3.5"))
            .contactEmail("email@abcd.com")
            .contactPhone("02-1234-5678")
            .createdAt(LocalDateTime.now())
            .build();
    }

    private Brand registProductBrand() {
        return Brand.builder()
            .id(1L)
            .name("TestBrand")
            .slug("test-brand")
            .description("test Brand")
            .logoUrl("https://example.com/logos/testbrand.png")
            .website("https://testbrnd.com")
            .build();
    }

    private Product registProductProduct() {
        return Product.builder()
            .id(1L)
            .name("Product")
            .slug("product-slug")
            .shortDescription("short")
            .fullDescription("full")
            .seller(seller)
            .brand(brand)
            .status(ProductStatus.ACTIVE)
            .categories(categories)
            .detail(detail)
            .price(price)
            .images(images)
            .tags(tags)
            .build();
    }

    private ProductPrice registProductPrice() {
        ProductPriceRequest request = new ProductPriceRequest(new BigDecimal(100000), new BigDecimal(90000),
            new BigDecimal(40000), "KRW", new BigDecimal("10.0"));
        return ProductPrice.of(null, request);
    }

    private ProductDetail registProductDetail() {
        DimensionsRequest request = new DimensionsRequest(10, 20, 30);
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("assembly_test", "test");
        return ProductDetail.of(null, new BigDecimal(60), Dimensions.of(request),
            "detail", "테스트", "1년", "테스트", additionalInfo);
    }

    private List<ProductCategory> registProductCategories() {
        Category category = Category.of("test", "category-slug", "categoryDescription", null, 1,
            "imageUrl");
        ProductCategory productCategory = ProductCategory.of(null, category, true);
        return List.of(productCategory);
    }

    private List<ProductImage> registProductImages() {
        return List.of();
    }

    private List<ProductTag> registProductTags() {
        return List.of();
    }

    private Product registProductReleateProduct() {
        return Product.builder()
            .id(2L)
            .name("relate")
            .slug("relate-product-slug")
            .shortDescription("short")
            .fullDescription("full")
            .seller(seller)
            .brand(brand)
            .status(ProductStatus.ACTIVE)
            .categories(categories)
            .detail(detail)
            .price(price)
            .images(images)
            .tags(tags)
            .build();
    }

}