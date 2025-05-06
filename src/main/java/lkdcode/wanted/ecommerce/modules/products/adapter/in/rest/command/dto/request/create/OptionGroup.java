package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductOptionGroupModel;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductOptionList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrder;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupName;

import java.util.List;

public record OptionGroup(
    @NotNull
    @Size(max = ProductOptionGroupName.LENGTH)
    String name,

    Integer displayOrder,

    List<Option> options
) {
    public ProductOptionGroupModel getProductOptionGroupModel() {
        return new ProductOptionGroupModel(
            new ProductOptionGroupName(name),
            new ProductOptionGroupDisplayOrder(displayOrder),
            new ProductOptionList(
                options.stream()
                    .map(Option::getProductOptionModel)
                    .toList()
            )
        );
    }
}