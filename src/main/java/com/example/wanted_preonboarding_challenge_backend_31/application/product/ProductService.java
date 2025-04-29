package com.example.wanted_preonboarding_challenge_backend_31.application.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductImage;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOption;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOptionGroup;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductImageCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductOptionCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductOptionUpdateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductDetailRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductImageCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductOptionCreateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductOptionUpdateRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchRes;
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
    private final ProductComplexQueryService productComplexQueryService;

    public ProductCreateRes create(ProductCreateReq req) {
        Product product = productCommandService.saveProduct(req);
        createProductRelationEntities(req, product);

        return ProductCreateRes.from(product);
    }

    public ProductUpdateRes update(Long productId, ProductCreateReq req) {
        Product product = productQueryService.getProductById(productId);
        productCommandService.clearProductRelations(product);

        product = productCommandService.updateProduct(product, req);
        createProductRelationEntities(req, product);

        return ProductUpdateRes.from(product);
    }

    private void createProductRelationEntities(ProductCreateReq req, Product product) {
        productCommandService.saveProductDetail(product, req.detail());
        productCommandService.saveProductPrice(product, req.price());
        productCommandService.saveProductCategories(product, req.categories());
        productCommandService.saveProductOptionGroups(product, req.optionGroups());
        productCommandService.saveProductImages(product, req.images());
        productCommandService.saveProductTags(product, req.tags());
    }

    public ProductSearchRes search(PaginationReq paginationReq, ProductSearchReq req) {
        return productComplexQueryService.searchProducts(paginationReq, req);
    }

    public ProductDetailRes detail(Long productId) {
        return productComplexQueryService.detailProduct(productId);
    }

    public void delete(Long productId) {
        Product product = productQueryService.getProductById(productId);
        productCommandService.clearProductRelations(product);

        productCommandService.deleteProduct(product);
    }

    public ProductOptionCreateRes createOption(Long productId, ProductOptionCreateReq req) {
        ProductOptionGroup group = productQueryService.getProductOptionGroupByIdAndProductId(req.optionGroupId(),
                productId);
        ProductOptionDto productOptionDto = ProductOptionDto.from(req);

        ProductOption productOption = productCommandService.saveProductOption(group, productOptionDto);
        return ProductOptionCreateRes.from(group.getId(), productOption);
    }

    public ProductOptionUpdateRes updateOption(Long productId, Long optionId, ProductOptionUpdateReq req) {
        ProductOption productOption = productQueryService.getProductOptionByIdAndProductId(optionId,
                productId);
        Long groupId = productQueryService.getProductOptionGroupIdByProductOptionId(
                productOption.getId());

        productOption = productCommandService.updateProductOption(productOption, req);
        return ProductOptionUpdateRes.from(groupId, productOption);
    }

    public void deleteOption(Long productId, Long optionId) {
        ProductOption productOption = productQueryService.getProductOptionByIdAndProductId(optionId,
                productId);

        productCommandService.deleteProductOption(productOption);
    }

    public ProductImageCreateRes createImage(Long productId, ProductImageCreateReq req) {
        Product product = productQueryService.getProductById(productId);

        ProductImage productImage = productCommandService.saveProductImage(product, ProductImageDto.from(req));
        return ProductImageCreateRes.from(productImage, req.optionId());
    }
}
