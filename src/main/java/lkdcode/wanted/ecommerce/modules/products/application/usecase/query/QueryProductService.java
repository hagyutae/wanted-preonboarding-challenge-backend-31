package lkdcode.wanted.ecommerce.modules.products.application.usecase.query;

import lkdcode.wanted.ecommerce.modules.products.application.ports.out.query.QueryProductDetailOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.query.QueryProductListOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.ProductValidator;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail.QueryProductDetailResult;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list.QueryProductListResult;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.QueryParamConditions;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryProductService {
    private final QueryProductListOutPort listPort;
    private final QueryProductDetailOutPort detailPort;
    private final ProductValidator validator;

    public QueryProductListResult getPreviewList(final Pageable pageable, final QueryParamConditions queryParamConditions) {
        return listPort.loadList(pageable, queryParamConditions);
    }

    public QueryProductDetailResult getProductDetail(final ProductId id) {
        return QueryProductDetailUsecase.execute(id)
            .validProductId(validator::existsProduct)
            .read(detailPort::load);
    }
}