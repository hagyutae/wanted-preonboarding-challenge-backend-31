package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.query;

import lkdcode.wanted.ecommerce.framework.common.api.response.ApiResponse;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.QueryProductService;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.ParamCondition;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.QueryParamConditions;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QueryProductApi {

    private final QueryProductService service;

    @GetMapping("/api/products")
    public ResponseEntity<?> getProductList(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable,

        @RequestParam(required = false) final ProductStatus status,
        @RequestParam(required = false) final Integer minPrice,
        @RequestParam(required = false) final Integer maxPrice,
        @RequestParam(required = false) final List<Integer> category,
        @RequestParam(required = false) final Integer seller,
        @RequestParam(required = false) final Integer brand,
        @RequestParam(required = false) final Boolean inStock,
        @RequestParam(required = false) final String search
    ) {
        final var response = service.getPreviewList(pageable, QueryParamConditions.of(
            ParamCondition.of("status", status),
            ParamCondition.of("minPrice", minPrice),
            ParamCondition.of("maxPrice", maxPrice),
            ParamCondition.of("category", category),
            ParamCondition.of("seller", seller),
            ParamCondition.of("brand", brand),
            ParamCondition.of("inStock", inStock),
            ParamCondition.of("search", search)
        ));

        return ResponseEntity.ok(
            ApiResponse.success(response, "상품 목록을 성공적으로 조회했습니다.")
        );
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<?> getProductDetail(
        @PathVariable(name = "id") final Long id
    ) {
        final var response = service.getProductDetail(new ProductId(id));

        return ResponseEntity.ok(
            ApiResponse.success(response, "상품 목록을 성공적으로 조회했습니다.")
        );
    }
}