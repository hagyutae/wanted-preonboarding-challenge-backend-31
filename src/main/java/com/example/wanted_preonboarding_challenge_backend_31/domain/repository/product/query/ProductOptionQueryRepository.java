package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.query;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionDetailDto;
import java.util.List;
import java.util.Map;

public interface ProductOptionQueryRepository {

    Map<Long, List<ProductOptionDetailDto>> getProductOptionDetails(List<Long> productOptionGroupIds);
}
