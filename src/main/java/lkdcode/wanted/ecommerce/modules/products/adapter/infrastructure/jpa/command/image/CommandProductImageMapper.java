package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command.image;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductImageJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.image.AddImageResult;
import lkdcode.wanted.ecommerce.modules.products.domain.model.image.AddImageModel;
import org.springframework.stereotype.Service;

@Service
public class CommandProductImageMapper {

    public ProductImageJpaEntity convert(final ProductJpaEntity product, final ProductOptionJpaEntity option, final AddImageModel model) {
        return ProductImageJpaEntity.builder()
            .product(product)
            .url(model.url().value())
            .altText(model.altText().value())
            .isPrimary(model.isPrimary().value())
            .displayOrder(model.displayOrder().value())
            .option(option)
            .build();
    }

    public AddImageResult convert(final ProductImageJpaEntity target) {
        return AddImageResult.builder()
            .id(target.getId())
            .url(target.getUrl())
            .alt_text(target.getAltText())
            .is_primary(target.getIsPrimary())
            .display_order(target.getDisplayOrder())
            .option_id(target.getDisplayOrder())
            .build();
    }
}