package kr.co.wanted.backend31.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.wanted.backend31.repository.product.ProductRepository;
import kr.co.wanted.backend31.service.vo.Pagenation;
import kr.co.wanted.backend31.service.vo.ProductSearchCondition;
import kr.co.wanted.backend31.service.vo.ProductSearchInfo;
import kr.co.wanted.backend31.service.vo.ProductSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductSearchService {
    private final ProductRepository productRepository;

    public ProductSearchResult searchPage(ProductSearchCondition cond) {
        final var resp = productRepository.searchPage(cond.status(), cond.seller(), cond.brand(), cond.minPrice(),
                cond.maxPrice(), cond.pageable());
        return ProductSearchResult.builder()
                .items(resp.getContent().stream().map(ProductSearchInfo::of).toList())
                .pagenation(Pagenation.builder()
                        .totalItems(resp.getTotalElements())
                        .totalPages(resp.getTotalPages())
                        .currentPage(resp.getNumber() + 1)
                        .perPage(resp.getSize())
                        .build())
                .build();
    }

}
