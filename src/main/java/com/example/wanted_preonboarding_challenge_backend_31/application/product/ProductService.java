package com.example.wanted_preonboarding_challenge_backend_31.application.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductCreateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductCommandService productCommandService;

    public ProductCreateRes create(ProductCreateReq req) {
        Product product = productCommandService.saveProduct(req);
        productCommandService.saveProductDetail(product, req.detail());
        productCommandService.saveProductPrice(product, req.price());
        productCommandService.saveProductCategories(product, req.categories());
        productCommandService.saveProductOptionGroups(product, req.optionGroups());
        productCommandService.saveProductImages(product, req.images());
        productCommandService.saveProductTags(product, req.tags());

        return ProductCreateRes.of(product);
    }
}
