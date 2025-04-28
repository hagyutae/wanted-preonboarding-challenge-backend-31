package sample.challengewanted.api.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.api.controller.product.response.ProductResponse;
import sample.challengewanted.api.service.brand.BrandService;
import sample.challengewanted.api.service.category.CategoryService;
import sample.challengewanted.api.service.category.ProductCategoryService;
import sample.challengewanted.api.service.seller.SellerService;
import sample.challengewanted.domain.brand.Brand;
import sample.challengewanted.domain.category.Category;
import sample.challengewanted.domain.product.entity.*;
import sample.challengewanted.domain.product.repository.ProductDetailRepository;
import sample.challengewanted.domain.product.repository.ProductPriceRepository;
import sample.challengewanted.domain.product.repository.ProductRepository;
import sample.challengewanted.domain.seller.Seller;
import sample.challengewanted.dto.ProductSearchCondition;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;
    private final SellerService sellerService;
    private final BrandService brandService;
    private final ProductDetailRepository productDetailRepository;
    private final CategoryService categoryService;
    private final ProductCategoryService productCategoryService;
    private final ProductImageService imageService;
    private final ProductOptionGroupService productOptionGroupService;
    private final ProductTagService tagService;

    public ProductResponse createProduct(ProductCreateRequest request) {
        Seller seller = sellerService.findById(request);
        Brand brand = brandService.findById(request);
        List<Category> categories = categoryService.findByIdIn(request);

        Product product = productRepository.save(Product.create(request, seller, brand));
        productDetailRepository.save(ProductDetail.create(request.getDetail()));
        productPriceRepository.save(ProductPrice.create(request.getPrice(), product));
        productCategoryService.saveProductCategories(request, categories, product);

        List<ProductOption> options = productOptionGroupService.saveGroupWithOptions(request, product);
        imageService.save(request, options, product);
        tagService.saveProductTags(request, product);

        return ProductResponse.from(product);
    }

    public Page<?> selectProducts(ProductSearchCondition condition, Pageable pageable) {
        return productRepository.searchProducts(condition, pageable);
    }
}
