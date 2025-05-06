package lkdcode.wanted.ecommerce.modules.products.application.ports.out.query;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list.QueryProductListResult;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.QueryParamConditions;
import org.springframework.data.domain.Pageable;

public interface QueryProductListOutPort {
    QueryProductListResult loadList(Pageable pageable, QueryParamConditions queryParamConditions);
}
