package investLee.platform.ecommerce.service;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ProductImageEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionEntity;
import investLee.platform.ecommerce.dto.request.ProductImageCreateRequest;
import investLee.platform.ecommerce.dto.request.ProductImageUpdateRequest;
import investLee.platform.ecommerce.repository.ProductImageRepository;
import investLee.platform.ecommerce.repository.ProductOptionRepository;
import investLee.platform.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final ProductOptionRepository optionRepository;

    @Transactional
    public void addImages(Long productId, List<ProductImageCreateRequest> dtos) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품 없음"));

        for (ProductImageCreateRequest dto : dtos) {
            ProductOptionEntity option = null;
            if (dto.getOptionId() != null) {
                option = optionRepository.findById(dto.getOptionId())
                        .orElseThrow(() -> new EntityNotFoundException("옵션 없음"));
            }

            ProductImageEntity image = ProductImageEntity.builder()
                    .product(product)
                    .url(dto.getUrl())
                    .altText(dto.getAltText())
                    .isPrimary(dto.isPrimary())
                    .displayOrder(dto.getDisplayOrder())
                    .option(option)
                    .build();

            imageRepository.save(image);
        }
    }

    @Transactional
    public void updateImage(Long productId, Long imageId, ProductImageUpdateRequest dto) {
        ProductImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("이미지 없음"));

        if (!image.getProduct().getId().equals(productId)) {
            throw new IllegalStateException("해당 상품에 속하지 않은 이미지입니다.");
        }

        ProductOptionEntity option = null;
        if (dto.getOptionId() != null) {
            option = optionRepository.findById(dto.getOptionId())
                    .orElseThrow(() -> new EntityNotFoundException("옵션 ID 없음"));
        }

        image.update(dto, option);
    }

    @Transactional
    public void deleteImage(Long productId, Long imageId) {
        ProductImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("이미지 없음"));

        if (!image.getProduct().getId().equals(productId)) {
            throw new IllegalStateException("해당 상품에 속하지 않은 이미지입니다.");
        }

        imageRepository.delete(image);
    }
}