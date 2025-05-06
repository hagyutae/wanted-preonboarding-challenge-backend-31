package lkdcode.wanted.ecommerce.modules.products.application.usecase.command.delete;

import lkdcode.wanted.ecommerce.modules.products.application.ports.out.command.DeleteProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteProductService {
    private final DeleteProductOutPort port;

    public void delete(final ProductId productId) {
        DeleteProductUsecase.execute(productId)
            .delete(port::delete)
            .done();
    }
}
