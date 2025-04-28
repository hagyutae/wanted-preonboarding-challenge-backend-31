package com.example.wanted_preonboarding_challenge_backend_31.web.product;

import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_CREATE;
import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_DELETE;
import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_IMAGE_CREATE;
import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_OPTION_CREATE;
import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_OPTION_DELETE;
import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_OPTION_UPDATE;
import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_SEARCH;
import static com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.ProductSuccessType.PRODUCT_UPDATE;

import com.example.wanted_preonboarding_challenge_backend_31.application.product.ProductService;
import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductImageCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductOptionCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductOptionUpdateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductImageCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductOptionCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductOptionUpdateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductUpdateRes;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public SuccessRes<ProductSearchRes> search(
            @RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
            @RequestParam(name = "perPage", defaultValue = "10") @Min(1) int perPage,
            @ModelAttribute @Validated ProductSearchReq productSearchReq
    ) {
        PaginationReq paginationReq = new PaginationReq(page, perPage);
        return SuccessRes.of(PRODUCT_SEARCH, productService.search(paginationReq, productSearchReq));
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

    @PostMapping("/{id}/options")
    public SuccessRes<ProductOptionCreateRes> optionCreate(
            @PathVariable("id") Long productId,
            @RequestBody @Validated ProductOptionCreateReq productOptionCreateReq
    ) {
        return SuccessRes.of(PRODUCT_OPTION_CREATE, productService.createOption(productId, productOptionCreateReq));
    }

    @PutMapping("/{id}/options/{optionId}")
    public SuccessRes<ProductOptionUpdateRes> optionUpdate(
            @PathVariable("id") Long productId,
            @PathVariable("optionId") Long optionId,
            @RequestBody @Validated ProductOptionUpdateReq productOptionUpdateReq
    ) {
        return SuccessRes.of(PRODUCT_OPTION_UPDATE,
                productService.updateOption(productId, optionId, productOptionUpdateReq));
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public SuccessRes<?> optionDelete(
            @PathVariable("id") Long productId,
            @PathVariable("optionId") Long optionId
    ) {
        productService.deleteOption(productId, optionId);
        return SuccessRes.of(PRODUCT_OPTION_DELETE);
    }

    @PostMapping("/{id}/images")
    public SuccessRes<ProductImageCreateRes> imageCreate(
            @PathVariable("id") Long productId,
            @RequestBody @Validated ProductImageCreateReq productImageCreateReq
    ) {
        return SuccessRes.of(PRODUCT_IMAGE_CREATE, productService.createImage(productId, productImageCreateReq));
    }
}
