package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.ImageValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrderList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ImageValidatorAdapter implements ImageValidator {

    @Override
    public void validImageDisplayOrder(ProductImageDisplayOrderList list) {
        validDuplicate(list);
        validIncrement(list);
    }

    private static void validIncrement(final ProductImageDisplayOrderList list) {
        final var expected = new AtomicInteger(list.list().get(0).value());

        list.forEach(e -> {
            final var value = e.value();
            if (value != expected.getAndIncrement()) {
                throw new ApplicationException(ApplicationResponseCode.INVALID_IMAGE_DISPLAY_ORDER);
            }
        });
    }

    private static void validDuplicate(final ProductImageDisplayOrderList list) {
        final var set = new HashSet<>(list.list());

        if (set.size() != list.size()) {
            throw new ApplicationException(ApplicationResponseCode.DUPLICATE_IMAGE_DISPLAY_ORDER);
        }
    }
}