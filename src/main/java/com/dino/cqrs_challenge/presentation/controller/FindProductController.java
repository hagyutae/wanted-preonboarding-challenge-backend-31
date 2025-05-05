package com.dino.cqrs_challenge.presentation.controller;

import com.dino.cqrs_challenge.global.response.ApiResponse;
import com.dino.cqrs_challenge.global.response.PaginatedApiResponse;
import com.dino.cqrs_challenge.presentation.model.dto.FindProductDTO;
import com.dino.cqrs_challenge.presentation.model.dto.FindProductSearchDTO;
import com.dino.cqrs_challenge.presentation.model.rq.ProductSearchCondition;
import com.dino.cqrs_challenge.presentation.service.FindProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "상품 관리 조회", description = "상품 관리 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FindProductController {


    private final FindProductService findProductService;

    @Operation(summary = "상품 목록 조회", description = "검색, 필터 조건, 정렬, 페이지네이션을 지원하는 상품 목록 조회")
    @GetMapping("/products")
    public ApiResponse<PaginatedApiResponse<FindProductSearchDTO>> getProducts(
            @ParameterObject
            @ModelAttribute
            ProductSearchCondition productSearchCondition
    ) {
        PaginatedApiResponse<FindProductSearchDTO> productsBySearchCondition =
                findProductService.findProductsBySearchCondition(productSearchCondition);

        return ApiResponse.<PaginatedApiResponse<FindProductSearchDTO>>builder()
                .success(true)
                .data(productsBySearchCondition)
                .message("상품 목록을 성공적으로 조회했습니다.")
                .build();
    }

    @Operation(summary = "상품 상세 조회", description = "상품 ID를 기반으로 상품 상세 정보 조회")
    @GetMapping("/products/{id}")
    public ApiResponse<FindProductDTO> getProduct(@PathVariable Long id) {
        FindProductDTO responseData = findProductService.findProductById(id);

        return ApiResponse.<FindProductDTO>builder()
                .success(true)
                .data(responseData)
                .message("상품 상세 정보를 성공적으로 조회했습니다.")
                .build();
    }

}
