package minseok.cqrschallenge.product.service;

import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.dto.request.ProductImageCreateRequest;
import minseok.cqrschallenge.product.dto.response.ProductImageResponse;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductImage;
import minseok.cqrschallenge.product.entity.ProductOption;
import minseok.cqrschallenge.product.repository.ProductImageRepository;
import minseok.cqrschallenge.product.repository.ProductOptionRepository;
import minseok.cqrschallenge.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final ProductOptionRepository optionRepository;

    @Override
    @Transactional
    public ProductImageResponse addProductImage(Long productId, ProductImageCreateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));

        ProductOption option = null;
        if (request.getOptionId() != null) {
            option = optionRepository.findById(request.getOptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("요청한 옵션을 찾을 수 없습니다."));

            if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
                throw new ResourceNotFoundException("해당 상품에 속한 옵션이 아닙니다.");
            }
        }

        if (request.getIsPrimary() != null && request.getIsPrimary()) {
            imageRepository.findByProductIdAndIsPrimaryTrue(productId)
                    .ifPresent(primaryImage -> {
                        primaryImage.unmarkAsPrimary();
                        imageRepository.save(primaryImage);
                    });
        }

        ProductImage image = ProductImage.builder()
                .product(product)
                .url(request.getUrl())
                .altText(request.getAltText())
                .isPrimary(request.getIsPrimary())
                .displayOrder(request.getDisplayOrder())
                .option(option)
                .build();

        ProductImage savedImage = imageRepository.save(image);

        return convertToResponse(savedImage);
    }

    private ProductImageResponse convertToResponse(ProductImage image) {
        return ProductImageResponse.builder()
                .id(image.getId())
                .url(image.getUrl())
                .altText(image.getAltText())
                .isPrimary(image.getIsPrimary())
                .displayOrder(image.getDisplayOrder())
                .optionId(image.getOption() != null ? image.getOption().getId() : null)
                .build();
    }
}