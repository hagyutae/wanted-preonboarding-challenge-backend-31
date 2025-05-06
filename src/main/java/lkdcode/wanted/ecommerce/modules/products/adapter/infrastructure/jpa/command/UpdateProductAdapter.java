package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.external.brand.QueryBrandAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.category.QueryCategoryAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.seller.QuerySellerAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.tag.QueryTagAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command.*;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.*;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.command.UpdateProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductAdapter implements UpdateProductOutPort {
    private final CommandProductJpaRepository commandRepository;
    private final QueryProductJpaRepository queryRepository;

    private final QueryProductDetailJpaRepository queryProductDetailJpaRepository;
    private final QueryProductOptionGroupJpaRepository queryProductOptionGroupJpaRepository;
    private final QueryProductOptionJpaRepository queryProductOptionJpaRepository;
    private final QueryProductPriceJpaRepository queryProductPriceJpaRepository;

    private final QueryBrandAdapter queryBrandAdapter;
    private final QuerySellerAdapter querySellerAdapter;
    private final QueryTagAdapter queryTagAdapter;
    private final QueryCategoryAdapter queryCategoryAdapter;

    private final CommandProductCategoryJpaRepository categoryJpaRepository;
    private final CommandProductDetailJpaRepository detailJpaRepository;
    private final CommandProductOptionGroupJpaRepository optionGroupJpaRepository;
    private final CommandProductOptionJpaRepository optionJpaRepository;
    private final CommandProductImageJpaRepository imageJpaRepository;
    private final CommandProductPriceJpaRepository priceJpaRepository;
    private final CommandProductTagJpaRepository tagJpaRepository;

    private final CommandProductMapper mapper;

    @Override
    public UpsertResult update(ProductId productId, ProductValues values) {
        final var target = queryRepository.loadById(productId.value());
        target.update(values);

        return mapper.convertUpsertResult(target);
    }

    @Override
    public void updateCategory(ProductId id, ProductCategoryList list) {
        categoryJpaRepository.deleteAllByProduct_Id(id.value());

        final var productJpaEntity = queryRepository.loadById(id.value());

        list.forEach(model -> {
            final var categoryEntity = queryCategoryAdapter.load(model.id().value());
            categoryJpaRepository.save(
                mapper.convert(productJpaEntity, model, categoryEntity)
            );
        });
    }

    @Override
    public void updateDetail(ProductId id, ProductDetailModel model) {
        final var target = queryProductDetailJpaRepository.loadByProductId(id.value());

        target.update(model);
    }

    @Override
    public void updateOption(ProductId id, ProductOptionGroupList list) {
        optionGroupJpaRepository.deleteAllByProduct_id(id.value());

        final var productJpaEntity = queryRepository.loadById(id.value());

        list.forEach(groupModel -> {
            final var groupEntity = mapper.convert(productJpaEntity, groupModel);
            final var savedGroupEntity = optionGroupJpaRepository.save(groupEntity);

            optionGroupJpaRepository.flush();

            groupModel.optionListForEach(optionModel -> {
                final var optionEntity = mapper.convert(savedGroupEntity, optionModel);
                optionJpaRepository.save(optionEntity);

                optionJpaRepository.flush();
            });
        });
    }

    @Override
    public void updateImage(ProductId id, ProductImageList list) {
        imageJpaRepository.deleteAllByProduct_id(id.value());

        final var productJpaEntity = queryRepository.loadById(id.value());

        list.forEach(
            model -> {
                final var optionId = model.optionId();
                ProductOptionJpaEntity optionEntity = null;

                if (optionId != null) {
                    optionEntity = queryProductOptionJpaRepository
                        .findById(model.optionId().value())
                        .orElse(null);
                }

                final var entity = mapper.convert(productJpaEntity, model, optionEntity);
                imageJpaRepository.save(entity);
            }
        );
    }

    @Override
    public void updatePrice(ProductId id, ProductPriceModel model) {
        final var priceEntity = queryProductPriceJpaRepository.loadByProductId(id.value());
        priceEntity.update(model);
    }

    @Override
    public void updateTag(ProductId id, ProductTagList list) {
        tagJpaRepository.deleteAllByProduct_id(id.value());

        final var productJpaEntity = queryRepository.loadById(id.value());

        list.stream()
            .map(e -> queryTagAdapter.load(e.value()))
            .forEach(tagEntity ->
                tagJpaRepository.save(
                    mapper.convert(productJpaEntity, tagEntity)
                )
            );
    }
}