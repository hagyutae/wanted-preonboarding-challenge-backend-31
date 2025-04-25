package com.example.wanted_preonboarding_challenge_backend_31.web.product;

import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.*;

import com.example.wanted_preonboarding_challenge_backend_31.application.product.ProductService;
import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductUpdateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public SuccessRes<ProductCreateRes> create(@RequestBody @Validated ProductCreateReq productCreateReq) {
        return SuccessRes.of(PRODUCT_CREATE, productService.create(productCreateReq));
    }

    @PutMapping("/{id}")
    public SuccessRes<ProductUpdateRes> update(
            @PathVariable("id") Long productId,
            @RequestBody @Validated ProductCreateReq productCreateReq
    ) {
        return SuccessRes.of(PRODUCT_UPDATE, productService.update(productId, productCreateReq));
    }

    @DeleteMapping("/{id}")
    public SuccessRes<?> delete(@PathVariable("id") Long productId) {
        productService.delete(productId);
        return SuccessRes.of(PRODUCT_DELETE);
    }
}
