package com.example.demo.productoption.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.productoption.controller.request.AddProductOptionRequest;
import com.example.demo.productoption.controller.request.UpdateProductOptionRequest;
import com.example.demo.productoption.dto.ProductAddResult;
import com.example.demo.productoption.dto.ProductUpdateResult;
import com.example.demo.productoption.service.ProductOptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/products/{id}")
@RequiredArgsConstructor
public class ProductOptionController {
    private final ProductOptionService productOptionService;

    // TODO : ACCESS TOKEN
    @PostMapping("/options")
    public ResponseEntity<ApiResponse<ProductAddResult>> createProductOption(@Positive @PathVariable("id") Long id,
                                                                             @Valid @RequestBody AddProductOptionRequest addProductOptionRequest) {

        ProductAddResult productAddResult = productOptionService.addOption(id, addProductOptionRequest);
        return new ResponseEntity<>(
                ApiResponse.success(productAddResult, "상품 옵션이 성공적으로 추가되었습니다."),
                HttpStatus.CREATED
        );
    }

    // TODO : ACCESS TOKEN
    @PutMapping("/options/{optionId}")
    public ResponseEntity<ApiResponse<ProductUpdateResult>> updateProductOption(@Positive @PathVariable("id") Long id,
                                                    @Positive @PathVariable("optionId") Long optionId,
                                                    @Valid @RequestBody UpdateProductOptionRequest updateProductOptionRequest) {
        ProductUpdateResult productUpdateResult = productOptionService.update(id, optionId, updateProductOptionRequest);

        return new ResponseEntity<>(
                ApiResponse.success(productUpdateResult, "상품 옵션이 성공적으로 수정되었습니다."),
                HttpStatus.OK
        );
    }

    // TODO : ACCESS TOKEN
    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductOption(@Positive @PathVariable("id") Long id,
                                                    @Positive @PathVariable("optionId") Long optionId) {
        productOptionService.delete(id, optionId);

        return new ResponseEntity<>(
                ApiResponse.success(null, "상품 옵션이 성공적으로 삭제되었습니다."),
                HttpStatus.OK
        );
    }
}
