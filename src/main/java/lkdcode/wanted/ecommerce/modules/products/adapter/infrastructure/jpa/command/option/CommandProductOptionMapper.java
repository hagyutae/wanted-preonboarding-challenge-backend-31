package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command.option;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionGroupJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.AddOptionModel;
import org.springframework.stereotype.Service;

@Service
public class CommandProductOptionMapper {
    public ProductOptionJpaEntity convert(final ProductOptionGroupJpaEntity group, final AddOptionModel model) {
        return ProductOptionJpaEntity.builder()
            .optionGroup(group)
            .name(model.name().value())
            .additionalPrice(model.additionalPrice().value())
            .sku(model.sku().value())
            .stock(model.stock().value())
            .displayOrder(model.displayOrder().value())
            .build();
    }

    public CommandOptionResult convert(final ProductOptionJpaEntity target) {
        return CommandOptionResult.builder()
            .id(target.getId())
            .option_group_id(target.getOptionGroup().getId())
            .name(target.getName())
            .additional_price(target.getAdditionalPrice())
            .sku(target.getSku())
            .stock(target.getStock())
            .display_order(target.getDisplayOrder())
            .build();
    }
}