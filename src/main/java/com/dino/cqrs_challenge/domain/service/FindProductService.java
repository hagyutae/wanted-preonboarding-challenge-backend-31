package com.dino.cqrs_challenge.domain.service;

import com.dino.cqrs_challenge.domain.model.dto.FindProductDTO;
import com.dino.cqrs_challenge.domain.model.dto.FindProductSummaryDTO;
import com.dino.cqrs_challenge.domain.model.rq.ProductSearchCondition;
import com.dino.cqrs_challenge.global.response.PaginatedApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindProductService {

    public PaginatedApiResponse<FindProductSummaryDTO> findProductsBySearchCondition(ProductSearchCondition productSearchCondition) {
        // TODO 상품 목록 조회 로직 구현
        return PaginatedApiResponse.<FindProductSummaryDTO>builder()
                .items(List.of())
                .pagination(PaginatedApiResponse.Pagination.builder()
                        .build())
                .build();
    }

    public FindProductDTO findProductById(Integer id) {
        // TODO 상품 상세 조회 로직 구현
        return new FindProductDTO();
    }
}
