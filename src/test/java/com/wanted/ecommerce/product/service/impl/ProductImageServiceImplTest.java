package com.wanted.ecommerce.product.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductStatus;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductImageCreateResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import com.wanted.ecommerce.product.repository.ProductImageRepository;
import com.wanted.ecommerce.product.service.ProductOptionService;
import com.wanted.ecommerce.product.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductImageServiceImplTest {

    @InjectMocks
    private ProductImageServiceImpl imageService;
    @Mock
    private ProductImageRepository imageRepository;
    @Mock
    private ProductOptionService optionService;
    @Mock
    private ProductService productService;

    private Product product;
    private ProductOption option;
    private ProductImage image;

    @BeforeEach
    void setUp() {
        product = createProduct();
        option = createOption();
        image = createImage();
    }

    private Product createProduct() {
        return Product.builder()
            .id(1L)
            .name("Product")
            .slug("product-slug")
            .shortDescription("short")
            .fullDescription("full")
            .status(ProductStatus.ACTIVE)
            .build();
    }

    private ProductOption createOption() {
        return ProductOption.of(null, "option", new BigDecimal(10), "sku",
            10, 1);
    }

    private ProductImage createImage() {
        return ProductImage.builder()
            .id(1L)
            .product(product)
            .url("url")
            .altText("alt")
            .primary(true)
            .option(option)
            .displayOrder(1)
            .build();
    }

    @Test
    void test_createProductImages_success() {
        List<ProductImageRequest> requests = List.of(mock(ProductImageRequest.class));
        when(optionService.getOptionById(anyLong())).thenReturn(option);
        when(imageRepository.saveAll(any())).thenReturn(List.of(image));
        List<ProductImageCreateResponse> response = imageService.createProductImages(product,
            requests);
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).id());
    }

    @Test
    void test_createProductImage_success() {
        ProductImageRequest request = mock(ProductImageRequest.class);
        when(productService.getProductById(anyLong())).thenReturn(product);
        when(optionService.getOptionById(anyLong())).thenReturn(option);
        when(imageRepository.saveAll(any())).thenReturn(List.of(image));
        ProductImageCreateResponse response = imageService.createProductImages(product,
            List.of(request)).get(0);
        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void test_createPrimaryProductImageResponse_success() {
        when(imageRepository.findByProductIdAndPrimaryTrue(anyLong())).thenReturn(
            Optional.ofNullable(image));
        ProductImageResponse response = imageService.createPrimaryProductImageResponse(1L);
        assertNotNull(response);
    }

    @Test
    void test_deleteProductImageByProductId_success() {
        imageService.deleteProductImageByProductId(1L);
        verify(imageRepository).deleteByProductId(anyLong());
    }

    @Test
    void test_createImageResponse_success() {
        List<ProductImageCreateResponse> responses = imageService.createImageResponse(
            List.of(image));
        assertFalse(responses.isEmpty());
        assertEquals(image.getId(), responses.get(0).id());

    }
}