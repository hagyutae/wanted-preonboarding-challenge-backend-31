package com.example.wanted_preonboarding_challenge_backend_31.application.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductUpdateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductCreateRes create(ProductCreateReq req) {
        Product product = productCommandService.saveProduct(req);
        createProductRelationEntities(req, product);

        return ProductCreateRes.of(product);
    }

    public ProductUpdateRes update(Long productId, ProductCreateReq req) {
        Product product = productQueryService.getProductById(productId);
        productCommandService.clearProductRelations(product);

        productCommandService.updateProduct(product, req);
        createProductRelationEntities(req, product);

        return ProductUpdateRes.of(product);
    }

    private void createProductRelationEntities(ProductCreateReq req, Product product) {
        productCommandService.saveProductDetail(product, req.detail());
        productCommandService.saveProductPrice(product, req.price());
        productCommandService.saveProductCategories(product, req.categories());
        productCommandService.saveProductOptionGroups(product, req.optionGroups());
        productCommandService.saveProductImages(product, req.images());
        productCommandService.saveProductTags(product, req.tags());
    }

    public void delete(Long productId) {
        Product product = productQueryService.getProductById(productId);
        productCommandService.clearProductRelations(product);

        productCommandService.deleteProduct(product);
    }
}
