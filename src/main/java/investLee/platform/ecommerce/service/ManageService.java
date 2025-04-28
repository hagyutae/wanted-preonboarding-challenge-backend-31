package investLee.platform.ecommerce.service;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ProductImageEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionGroupEntity;
import investLee.platform.ecommerce.dto.request.*;
import org.springframework.transaction.annotation.Transactional;

public interface ManageService {
    Long createProduct(ProductCreateRequest request);
    void updateProduct(Long productId, ProductUpdateRequest request);
    void deleteProduct(Long productId);
    void addOption(Long productId, OptionCreateRequest request);
    void updateOption(Long optionId, OptionUpdateRequest request);
    void deleteOption(Long optionId);
    void addImage(Long productId, ImageCreateRequest request);
    void updateImage(Long imageId, ImageUpdateRequest request);
    void deleteImage(Long imageId);
}