package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.external.brand.QueryBrandAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.category.QueryCategoryAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.seller.QuerySellerAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.tag.QueryTagAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command.*;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductOptionJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.command.CreateProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductAdapter implements CreateProductOutPort {
    private final CommandProductJpaRepository commandRepository;
    private final QueryProductJpaRepository queryRepository;

    private final QueryProductOptionJpaRepository queryProductOptionJpaRepository;

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
    public UpsertResult save(ProductValues values) {
        final var seller = querySellerAdapter.load(values.sellerId().value());
        final var brand = queryBrandAdapter.load(values.brandId().value());
        final var entity = mapper.convert(values, seller, brand);
        final var saved = commandRepository.save(entity);

        return mapper.convertUpsertResult(saved);
    }

    @Override
    public void saveCategory(ProductId id, ProductCategoryList list) {
        final var productJpaEntity = queryRepository.loadById(id.value());

        list.forEach(model -> {
            final var categoryEntity = queryCategoryAdapter.load(model.id().value());
            categoryJpaRepository.save(
                mapper.convert(productJpaEntity, model, categoryEntity)
            );
        });
    }

    @Override
    public void saveDetail(ProductId id, ProductDetailModel model) {
        final var productJpaEntity = queryRepository.loadById(id.value());
        final var entity = mapper.convert(productJpaEntity, model);
        detailJpaRepository.save(entity);
    }

    @Override
    public void saveOption(ProductId id, ProductOptionGroupList list) {
        final var productJpaEntity = queryRepository.loadById(id.value());

        list.forEach(groupModel -> {

            final var groupEntity = mapper.convert(productJpaEntity, groupModel);
            final var savedGroupEntity = optionGroupJpaRepository.save(groupEntity);

            groupModel.optionListForEach(optionModel -> {
                final var optionEntity = mapper.convert(savedGroupEntity, optionModel);
                optionJpaRepository.save(optionEntity);
            });
        });
    }

    @Override
    public void saveImage(ProductId id, ProductImageList list) {
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
    public void savePrice(ProductId id, ProductPriceModel model) {
        final var productJpaEntity = queryRepository.loadById(id.value());
        final var entity = mapper.convert(productJpaEntity, model);
        priceJpaRepository.save(entity);
    }

    @Override
    public void saveTag(ProductId id, ProductTagList list) {
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