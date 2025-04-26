package com.example.wanted_preonboarding_challenge_backend_31.application.product;

import com.example.wanted_preonboarding_challenge_backend_31.application.brand.BrandQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.category.CategoryQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.seller.SellerQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.tag.TagQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.brand.Brand;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductCategory;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductDetail;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductImage;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOption;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOptionGroup;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductPrice;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductTag;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.seller.Seller;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.tag.Tag;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductCategoryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductDetailRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductImageRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductOptionGroupRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductOptionRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductPriceRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductTagRepository;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductCategoryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionGroupDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductPriceDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductOptionUpdateReq;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService {

    private final SellerQueryService sellerQueryService;
    private final BrandQueryService brandQueryService;
    private final CategoryQueryService categoryQueryService;
    private final ProductQueryService productQueryService;
    private final TagQueryService tagQueryService;

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductTagRepository productTagRepository;

    public Product saveProduct(ProductCreateReq req) {
        Seller seller = sellerQueryService.getById(req.sellerId());
        Brand brand = brandQueryService.getById(req.brandId());

        Product product = Product.create(req.name(), req.slug(), req.shortDescription(), req.fullDescription(), seller,
                brand, req.status());
        return productRepository.save(product);
    }

    public void saveProductDetail(Product product, ProductDetailDto req) {
        ProductDetail productDetail = ProductDetail.create(product, req.weight(), req.dimensions(), req.materials(),
                req.countryOfOrigin(), req.warrantyInfo(), req.careInstructions(), req.additionalInfo());
        productDetailRepository.save(productDetail);
    }

    public void saveProductPrice(Product product, ProductPriceDto req) {
        ProductPrice productPrice = ProductPrice.create(product, req.basePrice(), req.salePrice(), req.costPrice(),
                req.currency(), req.taxRate());
        productPriceRepository.save(productPrice);
    }

    public void saveProductCategories(Product product, List<ProductCategoryDto> categories) {
        List<ProductCategory> productCategories = categories.stream()
                .map(req -> {
                    Category category = categoryQueryService.getById(req.categoryId());
                    return ProductCategory.create(product, category, req.isPrimary());
                })
                .toList();
        productCategoryRepository.saveAll(productCategories);
    }

    public void saveProductOptionGroups(Product product, List<ProductOptionGroupDto> optionGroups) {
        for (ProductOptionGroupDto optionGroup : optionGroups) {
            ProductOptionGroup group = ProductOptionGroup.create(product, optionGroup.name(),
                    optionGroup.displayOrder());
            group = productOptionGroupRepository.save(group);
            saveProductOptions(group, optionGroup.options());
        }
    }

    public void saveProductOptions(ProductOptionGroup productOptionGroup, List<ProductOptionDto> options) {
        List<ProductOption> productOptions = options.stream()
                .map(req -> ProductOption.create(productOptionGroup, req.name(),
                        req.additionalPrice(), req.sku(), req.stock(), req.displayOrder()))
                .toList();
        productOptionRepository.saveAll(productOptions);
    }

    public ProductOption saveProductOption(ProductOptionGroup productOptionGroup, ProductOptionDto req) {
        ProductOption productOption = ProductOption.create(productOptionGroup, req.name(),
                req.additionalPrice(), req.sku(), req.stock(), req.displayOrder());
        return productOptionRepository.save(productOption);
    }

    public void saveProductImages(Product product, List<ProductImageDto> images) {
        List<ProductImage> productImages = images.stream()
                .map(req ->
                        ProductImage.create(
                                product,
                                req.url(),
                                req.altText(),
                                req.isPrimary(),
                                req.displayOrder(),
                                req.optionId() == null ? null
                                        : productQueryService.getProductOptionById(req.optionId())))
                .toList();
        productImageRepository.saveAll(productImages);
    }

    public void saveProductTags(Product product, List<Long> tagIds) {
        List<Tag> tags = tagQueryService.getAllByIds(tagIds);

        List<ProductTag> productTags = tags.stream()
                .map(tag -> ProductTag.create(product, tag))
                .toList();
        productTagRepository.saveAll(productTags);
    }

    public Product updateProduct(Product product, ProductCreateReq req) {
        Seller seller = sellerQueryService.getById(req.sellerId());
        Brand brand = brandQueryService.getById(req.brandId());

        product.update(req.name(), req.slug(), req.shortDescription(), req.fullDescription(), seller,
                brand, req.status());
        return productRepository.save(product);
    }

    public void clearProductRelations(Product product) {
        Long productId = product.getId();
        productDetailRepository.deleteByProductId(productId);
        productPriceRepository.deleteByProductId(productId);
        productCategoryRepository.deleteAllByProductId(productId);
        productOptionGroupRepository.deleteAllByProductId(productId);
        // ProductOption은 ProductOptionGroup cascade로 제거됨
        productImageRepository.deleteAllByProductId(productId);
        productTagRepository.deleteAllByProductId(productId);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public ProductOption updateProductOption(ProductOption productOption, ProductOptionUpdateReq req) {
        productOption.update(req.name(), req.additionalPrice(), req.sku(), req.stock(), req.displayOrder());
        return productOptionRepository.save(productOption);
    }
}
