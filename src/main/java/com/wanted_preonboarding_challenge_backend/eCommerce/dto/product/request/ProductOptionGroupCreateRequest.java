package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.OptionCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionGroupCreateRequest {
    private String name;
    private Integer displayOrder;
    private List<OptionCreateRequest> options;
}
