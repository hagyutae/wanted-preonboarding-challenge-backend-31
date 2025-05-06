package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command.image;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductImageJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command.CommandProductImageJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductImageJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductOptionJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.image.CommandImageOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.image.AddImageResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.image.AddImageModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddImageAdapter implements CommandImageOutPort {
    private final CommandProductImageJpaRepository commandProductImageJpaRepository;

    private final QueryProductJpaRepository queryProductJpaRepository;
    private final QueryProductOptionJpaRepository queryProductOptionJpaRepository;
    private final QueryProductImageJpaRepository queryProductImageJpaRepository;
    private final CommandProductImageMapper mapper;

    @Override
    public AddImageResult add(AddImageModel model) {
        final var product = queryProductJpaRepository.loadById(model.productId().value());
        final var option = model.optionId() == null
            ? null
            : queryProductOptionJpaRepository.loadById(model.optionId().value());

        final var image = mapper.convert(product, option, model);
        commandProductImageJpaRepository.save(image);

        return mapper.convert(image);
    }

    @Override
    public void unsetPrimary(ProductId productId) {
        queryProductImageJpaRepository
            .findAllByProduct_id(productId.value())
            .stream()
            .filter(ProductImageJpaEntity::getIsPrimary)
            .forEach(ProductImageJpaEntity::primaryToggle);
    }

    @Override
    public void shiftDisplayOrder(ProductId productId, ProductImageDisplayOrder productImageDisplayOrder) {
        queryProductImageJpaRepository
            .findAllByProduct_id(productId.value())
            .stream()
            .filter(e -> e.getDisplayOrder() >= productImageDisplayOrder.value())
            .forEach(ProductImageJpaEntity::incrementOrder);
    }
}