package com.preonboarding.challenge.service.product.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.entity.Product;
import com.preonboarding.challenge.entity.ProductStatus;
import com.preonboarding.challenge.exception.ResourceNotFoundException;
import com.preonboarding.challenge.repository.*;
import com.preonboarding.challenge.service.mapper.ProductMapper;
import com.preonboarding.challenge.service.product.ProductDto;
import com.preonboarding.challenge.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductCommandHandlerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProductOptionGroupRepository optionGroupRepository;

    @Mock
    private ProductOptionRepository optionRepository;

    @Mock
    private ProductImageRepository imageRepository;

    @Mock
    private ObjectMapper objectMapper;

    private ProductCommandHandler productCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productCommandHandler = new ProductService(
                productRepository,
                categoryRepository,
                brandRepository,
                sellerRepository,
                tagRepository,
                optionGroupRepository,
                optionRepository,
                imageRepository,
                new ProductMapper(objectMapper)
        );
    }

    @Test
    void createProduct_BasicProduct_ShouldReturnCreatedResult() {
        // Given
        ProductCommand.CreateProduct command = ProductCommand.CreateProduct.builder()
                .name("Test Product")
                .slug("test-product")
                .shortDescription("Short description")
                .fullDescription("Full description")
                .status(ProductStatus.ACTIVE.name())
                .build();
        command.setName("Test Product");
        command.setSlug("test-product");
        command.setStatus("ACTIVE");

        LocalDateTime now = LocalDateTime.now();

        // Product 저장 시 반환할 엔티티 설정
        when(productRepository.save(any())).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            if (product.getId() == null) {
                // ID 설정 (새로 생성된 경우)
                product.setId(1L);
                product.setCreatedAt(now);
                product.setUpdatedAt(now);
            }
            return product;
        });

        // When
        ProductDto.Product result = productCommandHandler.createProduct(command);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals("test-product", result.getSlug());

        // 저장 메서드 호출 확인
        verify(productRepository, times(2)).save(any());
    }

    @Test
    void updateProduct_ExistingProduct_ShouldReturnUpdatedResult() {
        // Given
        Long productId = 1L;
        ProductCommand.UpdateProduct command = ProductCommand.UpdateProduct.builder()
                .productId(productId)
                .name("Updated Product")
                .build();

        Product existingProduct = Product.builder()
                .id(productId)
                .name("Original Product")
                .slug("original-product")
                .status(ProductStatus.ACTIVE)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .optionGroups(new ArrayList<>())
                .categories(new ArrayList<>())
                .tags(new ArrayList<>())
                .images(new ArrayList<>())
                .build();

        LocalDateTime now = LocalDateTime.now();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setUpdatedAt(now);
            return product;
        });

        // When
        ProductDto.Product result = productCommandHandler.updateProduct(command);

        // Then
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Updated Product", result.getName());
        assertEquals("original-product", result.getSlug());

        // 저장 메서드 호출 확인
        verify(productRepository).findById(productId);
        verify(productRepository).save(any());
    }

    @Test
    void updateProduct_NonExistingProduct_ShouldThrowException() {
        // Given
        Long productId = 999L;
        ProductCommand.UpdateProduct command = ProductCommand.UpdateProduct.builder()
                .productId(productId)
                .name("Updated Product")
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            productCommandHandler.updateProduct(command);
        });

        // 저장 메서드 호출 안됨 확인
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProduct_ExistingProduct_ShouldMarkAsDeleted() {
        // Given
        Long productId = 1L;
        ProductCommand.DeleteProduct command = ProductCommand.DeleteProduct.builder()
                .productId(productId)
                .build();

        Product existingProduct = Product.builder()
                .id(productId)
                .name("Test Product")
                .slug("test-product")
                .status(ProductStatus.ACTIVE)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(existingProduct);

        // When
        productCommandHandler.deleteProduct(command);

        // Then
        assertEquals(ProductStatus.DELETED, existingProduct.getStatus());
        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void deleteProduct_NonExistingProduct_ShouldThrowException() {
        // Given
        Long productId = 999L;
        ProductCommand.DeleteProduct command = ProductCommand.DeleteProduct.builder()
                .productId(productId)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            productCommandHandler.deleteProduct(command);
        });

        // 저장 메서드 호출 안됨 확인
        verify(productRepository, never()).save(any());
    }
}