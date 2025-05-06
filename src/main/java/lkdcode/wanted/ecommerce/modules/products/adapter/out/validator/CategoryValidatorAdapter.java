package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.category.QueryCategoryAdapter;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.CategoryValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductCategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryValidatorAdapter implements CategoryValidator {
    private final QueryCategoryAdapter adapter;

    @Override
    public void validList(ProductCategoryList target) {
        target.forEach(model -> {
            final var entity = adapter.load(model.id().value());
            if (entity == null) {
                throw new ApplicationException(ApplicationResponseCode.NOT_FOUND_CATEGORY);
            }
        });

        final var count = target.stream()
            .filter(e -> e.isPrimary().value())
            .count();

        if (count > 1) throw new ApplicationException(ApplicationResponseCode.INVALID_CATEGORY_PRIMARY);
    }
}
