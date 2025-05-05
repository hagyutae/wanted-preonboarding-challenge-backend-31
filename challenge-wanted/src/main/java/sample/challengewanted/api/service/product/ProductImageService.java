package sample.challengewanted.api.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.product.request.ImageRequest;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.product.entity.Product;
import sample.challengewanted.domain.product.entity.ProductImage;
import sample.challengewanted.domain.product.entity.ProductOption;
import sample.challengewanted.domain.product.repository.ProductImageRepository;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Slf4j
@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public void save(ProductCreateRequest request, List<ProductOption> options, Product product) {
        List<ImageRequest> images = request.getImages();

        for (ImageRequest image : images) {
            ProductOption matchedOption = options.stream()
                    .filter(option -> option.getDisplayOrder().equals(image.getDisplayOrder()))
                    .findFirst()
                    .orElse(null); // 없을 수도 있으니 null 허용

            ProductImage productImage = ProductImage.create(
                    image.getUrl(),
                    image.getAltText(),
                    image.isPrimary(),
                    image.getDisplayOrder(),
                    product,
                    matchedOption
            );

            productImageRepository.save(productImage);
        }
    }

}
