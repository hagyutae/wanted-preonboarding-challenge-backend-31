package com.wanted.ecommerce.product.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.domain.ProductStatus;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.service.ProductOptionGroupService;
import com.wanted.ecommerce.product.service.ProductService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceImplTest {

    @InjectMocks
    private ProductOptionServiceImpl productOptionService;
    @Mock
    private ProductOptionRepository optionRepository;

    @Mock
    private ProductService productService;
    @Mock
    private ProductOptionGroupService optionGroupService;

    private ProductOptionGroup optionGroup;
    private ProductOption option;
    private Product product;

    @BeforeEach
    void setUp() {
        product = createProduct();
        optionGroup = createOptionGroup();
        option = createOption();
    }

    private ProductOptionGroup createOptionGroup() {
        return ProductOptionGroup.of(1L, product, "optionGroup", 1);
    }

    private ProductOption createOption() {
        return ProductOption.of(optionGroup, "option", new BigDecimal(10), "sku",
            10, 1);
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

    @Test
    void test_addProductOption_success() {
        ProductOptionRequest request = mock(ProductOptionRequest.class);
        ProductOptionGroup group = ProductOptionGroup.of(product, "optionGroup", 1);

        when(productService.getProductById(anyLong())).thenReturn(product);
        when(optionGroupService.updateOptionGroup(any(), anyLong())).thenReturn(group);
        when(optionRepository.save(any())).thenReturn(option);

        ProductOptionResponse response = productOptionService.addProductOption(product, request);
        assertNotNull(response);
    }

    @Test
    void test_updateProductOption_success() {
        ProductOptionRequest request = new ProductOptionRequest(1L, "option", new BigDecimal(10),
            "optionSku", 5, 1);
        when(optionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(option));
        ProductOptionResponse response = productOptionService.updateProductOption(1L, 1L, request);
        assertNotNull(response);
    }

    @Test
    void test_deleteProductOption_success() {
        when(optionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(option));
        productOptionService.deleteProductOption(1L, 1L);
        verify(optionRepository).delete(any());
    }

    @Test
    void test_isExistStock_success() {
        when(optionRepository.existsByOptionGroupProductIdAndStockGreaterThan(
            anyLong(), anyInt())).thenReturn(Boolean.TRUE);

       Boolean result =  productOptionService.isExistStock(1L, 0);
       assertNotNull(result);
       assertEquals(true, result);
    }

    @Test
    void test_getOptionById_success() {
        when(optionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(option));
        ProductOption result = productOptionService.getOptionById(1L);
        assertNotNull(result);
        assertEquals(option,result);
    }
}