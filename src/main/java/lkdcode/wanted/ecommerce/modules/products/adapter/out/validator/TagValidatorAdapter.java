package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.tag.QueryTagAdapter;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.TagValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagValidatorAdapter implements TagValidator {
    private final QueryTagAdapter adapter;

    @Override
    public void validList(ProductTagList list) {
        list.forEach(tag -> {
            if (adapter.load(tag.value()) == null) {
                throw new ApplicationException(ApplicationResponseCode.NOT_FOUND_TAG);
            }
        });
    }
}