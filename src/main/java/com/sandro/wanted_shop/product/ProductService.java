package com.sandro.wanted_shop.product;

import com.sandro.wanted_shop.brand.Brand;
import com.sandro.wanted_shop.brand.BrandRepository;
import com.sandro.wanted_shop.category.Category;
import com.sandro.wanted_shop.category.CategoryRepository;
import com.sandro.wanted_shop.product.dto.*;
import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.product.entity.ProductOption;
import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;
import com.sandro.wanted_shop.product.persistence.OptionGroupRepository;
import com.sandro.wanted_shop.product.persistence.OptionRepository;
import com.sandro.wanted_shop.product.persistence.ProductRepository;
import com.sandro.wanted_shop.seller.Seller;
import com.sandro.wanted_shop.seller.SellerRepository;
import com.sandro.wanted_shop.tag.Tag;
import com.sandro.wanted_shop.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Product register(CreateProductCommand command) {
        Seller seller = sellerRepository.findById(command.sellerId()).orElseThrow();
        Brand brand = brandRepository.findById(command.brandId()).orElseThrow();
        List<Tag> tags = tagRepository.findAllById(command.tagIdList());
        Map<Long, Category> categoryMap = categoryRepository.findAllById(command.categoryIdList()).stream()
                .collect(Collectors.toMap(Category::getId, Function.identity()));
        Product product = command.toEntity(seller, brand);
        product.addAllTags(tags);
        product.addAllCategories(command.categories(), categoryMap);
        return productRepository.save(product);
    }


    @Transactional
    public Product register(Product product) {
        return productRepository.save(product);
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId)
                .stream().map(ProductDto::from)
                .toList();
    }

    @Transactional
    public void updateProduct(Long id, UpdateProductCommand command) {
        Product product = productRepository
                .findById(id)
                .orElseThrow();
        product.update(command);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        optionRepository.deleteByOptionGroup_product_idAndId(productId, optionId);
    }

    @Transactional
    public void updateOption(Long productId, Long optionId, UpdateOptionCommand command) {
        ProductOption option = optionRepository
                .findByOptionGroup_product_idAndId(productId, optionId)
                .orElseThrow();
        option.update(command);
    }

    @Transactional
    public void addOption(Long productId, CreateOptionCommand command) {
        ProductOptionGroup productOptionGroup = optionGroupRepository
                .findByProduct_idAndId(productId, command.optionGroupId())
                .orElseThrow();
        command.toEntity(productOptionGroup);
    }

    @Transactional
    public void addImage(Long productId, List<CreateImageCommand> commands) {
        Product product = productRepository.findById(productId).orElseThrow();
        commands.parallelStream()
                .forEach(command -> command.toEntity(product));
    }

    // TODO: 쿼리 최적화하기
    public ProductDto getProductDetail(Long id) {
        return productRepository.findWithDetailById(id)
                .map(ProductDto::from)
                .orElseThrow();
    }

    public Page<ProductListDto> getAllProducts(Pageable pageable, ProductFilterDto filter) {
        return productRepository.findAll(pageable, filter)
                .map(ProductListDto::from);
    }
}
