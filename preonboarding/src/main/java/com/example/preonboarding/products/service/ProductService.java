package com.example.preonboarding.products.service;

import com.example.preonboarding.brands.domain.Brands;
import com.example.preonboarding.categories.domain.Categories;
import com.example.preonboarding.categories.domain.ProductCategories;
import com.example.preonboarding.details.domain.ProductDetails;
import com.example.preonboarding.categories.dto.ProductCategoriesDTO;
import com.example.preonboarding.details.dto.AdditionalInfo;
import com.example.preonboarding.details.dto.DetailDTO;
import com.example.preonboarding.details.dto.Dimensions;
import com.example.preonboarding.images.domain.ProductImages;
import com.example.preonboarding.images.dto.ProductImageDTO;
import com.example.preonboarding.options.domain.ProductOption;
import com.example.preonboarding.options.dto.ProductOptionDTO;
import com.example.preonboarding.price.dto.PriceDTO;
import com.example.preonboarding.options.domain.ProductOptionGroup;
import com.example.preonboarding.price.domain.ProductPrices;
import com.example.preonboarding.products.dto.ProductsDTO;
import com.example.preonboarding.reviews.domain.Reviews;
import com.example.preonboarding.reviews.dto.RatingDTO;
import com.example.preonboarding.tags.dto.TagsDTO;
import com.example.preonboarding.exception.NotFoundResourceException;
import com.example.preonboarding.exception.ProductRegisterException;
import com.example.preonboarding.products.domain.Products;
import com.example.preonboarding.brands.repository.BrandsRepository;
import com.example.preonboarding.categories.repository.CategoriesRepository;
import com.example.preonboarding.products.repository.ProductRepository;
import com.example.preonboarding.products.repository.ProductRepositoryCustom;
import com.example.preonboarding.reviews.repository.RatingRepository;
import com.example.preonboarding.seller.repository.SellerRepository;
import com.example.preonboarding.tags.repository.TagsRepository;
import com.example.preonboarding.request.CategoryRequest;
import com.example.preonboarding.request.ProductSearchRequest;
import com.example.preonboarding.request.ProductsRequest;
import com.example.preonboarding.response.*;
import com.example.preonboarding.response.error.ErrorCode;
import com.example.preonboarding.seller.domain.Sellers;
import com.example.preonboarding.tags.domain.ProductTags;
import com.example.preonboarding.tags.domain.Tags;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepositoryCustom productRepositoryCustom;
    private final RatingRepository ratingRepository;
    private final CategoriesRepository categoriesRepository;
    private final TagsRepository tagsRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final BrandsRepository brandsRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public Products addProducts(ProductsRequest request){

        //products
        Products products = Products.from(request);


        //brands
        Brands brand = productRepositoryCustom.findProductBrandById(request.getBrandId());
        products.setBrands(brand);

        //sellers
        Sellers seller = productRepositoryCustom.findProductSellerById(request.getSellerId());
        products.setSellers(seller);

        // detail
        try {
            ProductDetails details = ProductDetails.builder()
                    .weight(request.getDetail().getWeight())
                    .dimensions(objectMapper.valueToTree(request.getDetail().getDimensions()))
                    .materials(request.getDetail().getMaterials())
                    .warrantyInfo(request.getDetail().getWarrantyInfo())
                    .careInstructions(request.getDetail().getCareInstructions())
                    .additionalInfo(objectMapper.valueToTree(request.getDetail().getAdditionalInfo()))
                            .build();

            products.setProductDetails(details);
        } catch (Exception e) {
            throw new RuntimeException("제품 상세정보 JSON 직렬화 실패", e);
        }

        // price
        ProductPrices price = ProductPrices.builder()
                .basePrice(request.getPrice().getBasePrice())
                .salePrice(request.getPrice().getSalePrice())
                .costPrice(request.getPrice().getCostPrice())
                .currency(request.getPrice().getCurrency())
                .tax_rate(request.getPrice().getTax_rate()).build();

        products.setProductPrices(price);

        //images
        List<ProductImages> images = request.getImages().stream()
                .map(i-> ProductImages.builder()
                    .url(i.getUrl())
                    .altText(i.getAltText())
                    .displayOrder(i.getDisplayOrder())
                    .isPrimary(i.isPrimary())
                    .options(null)
                    .build())
                .collect(Collectors.toList());

       products.setProductImages(images);

       // categories
        List<ProductCategories> categories = request.getCategories().stream().map(i -> {
            Categories category = categoriesRepository.findById(i.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("not found category"));
            return ProductCategories.builder()
                    .categories(category)
                    .products(products)
                    .isPrimary(i.isPrimary())
                    .build();
        }).collect(Collectors.toList());

        products.setProductCategories(categories);

        //option groups
        List<ProductOptionGroup> optionGroups = request.getOptionGroups().stream().map(i -> {
            List<ProductOption> options = i.getOptions().stream()
                    .map(option -> {
                       return ProductOption.builder()
                                .name(option.getName())
                                .additionalPrice(option.getAdditionalPrice())
                                .sku(option.getSku())
                                .stock(option.getStock())
                                .displayOrder(option.getDisplayOrder())
                                .build();
                    }).collect(Collectors.toList());

            ProductOptionGroup optionGroup = ProductOptionGroup.builder()
                    .name(i.getName())
                    .displayOrder(i.getDisplayOrder())
                    .optionGroups(options)
                    .products(products)
                    .build();

            options.forEach(opt -> opt.setOptionGroups(optionGroup));

            return optionGroup;
        }).collect(Collectors.toList());

        products.setProductOptionGroups(optionGroups);

        //tags
        List<ProductTags> productTags = request.getTags().stream()
                .map(tagId -> {
                    Tags tag = tagsRepository.findById(tagId.longValue()).orElseThrow(() -> new IllegalArgumentException("not found tag :" + tagId));
                    return ProductTags.builder()
                            .tags(tag)
                            .products(products)
                            .build();
                }).collect(Collectors.toList());

        products.setProductTags(productTags);

        try {
            Products savedProducts = productRepository.save(products);
            return savedProducts;
        }catch (DataIntegrityViolationException e){
            Map<String, Object> details = new LinkedHashMap<>();
            details.put("name", "이미 등록된 상품입니다.");
            throw new ProductRegisterException(ErrorCode.INTERNAL_ERROR,details);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public List<ProductsSummaryResponse> findAllProducts(ProductSearchRequest search){
        List<ProductsDTO> products = productRepositoryCustom.searchPage(search);

        return products.stream()
                .map(o -> ProductsSummaryResponse.builder()
                        .id(o.getId())
                        .name(o.getName())
                        .slug(o.getSlug())
                        .shortDescription(o.getShortDescription())
                        .basePrice(o.getBasePrice())
                        .salesPrice(o.getSalesPrice())
                        .currency(o.getCurrency())
                        .primaryImage(ImageResponse.builder()
                                .url(o.getImages().getUrl())
                                .altText(o.getImages().getAltText()).build())
                        .brand(BrandResponse.builder()
                                .id(o.getBrandId())
                                .name(o.getBrandName())
                                .build())
                        .seller(SellerResponse.builder()
                                .id(o.getSellerId())
                                .name(o.getSellerName())
                                .build())
                        .rating(o.getReviews().getRating())
                        .reviewCount(o.getReviews().getReviewCount())
                        .inStock(o.getOptions().isStock())
                        .status(o.getStatus())
                        .createdAt(o.getCreatedAt())
                        .build()
                ).collect(Collectors.toList());
    }

    public ProductsDetailResponse findProductsById(Long id){
        Products product = productRepositoryCustom.findProductsById(id);

        ProductDetails productDetails = product.getProductDetails();

        Dimensions dimesions = new Dimensions();
        AdditionalInfo additionalInfo = new AdditionalInfo();

        try {
            if (productDetails.getDimensions() != null) {
                dimesions = objectMapper.treeToValue(productDetails.getDimensions(), Dimensions.class);

            }
        }catch (IOException e){
            throw new RuntimeException("dimensions 파싱 에러", e);
        }

        if (productDetails.getAdditionalInfo() != null) {
            additionalInfo = objectMapper.convertValue(productDetails.getAdditionalInfo(), AdditionalInfo.class);
        }

        DetailDTO detail = DetailDTO.builder()
                .weight(productDetails.getWeight())
                .dimensions(dimesions)
                .materials(productDetails.getMaterials())
                .countryOfOrigin(productDetails.getCountryOfOrigin())
                .warrantyInfo(productDetails.getWarrantyInfo())
                .careInstructions(productDetails.getCareInstructions())
                .additionalInfo(additionalInfo)
                .build();


        ProductPrices productPrices = product.getProductPrices();
        PriceDTO price = PriceDTO.builder()
                .basePrice(productPrices.getBasePrice())
                .salePrice(productPrices.getSalePrice())
                .costPrice(productPrices.getCostPrice())
                .currency(productPrices.getCurrency())
                .tax_rate(productPrices.getTax_rate())
                .build();



        List<ProductCategories> productCategories = product.getProductCategories();

        List<Long> parentIds = productCategories.stream()
                .map(i -> i.getCategories().getParentId())
                .distinct()
                .collect(Collectors.toList());

        Map<Long, Categories> parentCategoryMap = categoriesRepository.findAllById(parentIds).stream()
                .collect(Collectors.toMap(Categories::getId, Function.identity()));

        List<ProductCategoriesDTO> categories = productCategories.stream()
                .map(i -> {
                    return new ProductCategoriesDTO(i.getCategories(),
                            i.isPrimary(),
                            parentCategoryMap.get(i.getCategories().getParentId())) ;
                })
                .collect(Collectors.toList());



        List<ProductOptionGroup> productOptionGroups = product.getProductOptionGroups();
        List<ProductOptionDTO> optionDTOList = productOptionGroups.stream().map(i -> {
            return new ProductOptionDTO(i);
        }).collect(Collectors.toList());


        List<ProductImages> productImages = product.getProductImages();
        List<ProductImageDTO> images = productImages.stream().map(ProductImageDTO::new).collect(Collectors.toList());

        RatingDTO rating = ratingRepository.getRatingSummary(product.getId());



        List<ProductTags> productTags = product.getProductTags();

        List<TagsDTO> tags = productTags.stream().map(i -> {
            return TagsDTO.builder()
                    .id(i.getTags().getId())
                    .name(i.getTags().getName())
                    .slug(i.getTags().getSlug())
                    .build();


        }).collect(Collectors.toList());
        return ProductsDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .seller(new SellerResponse(product.getSellers()))
                .brand(new BrandResponse(product.getBrands()))
                .detail(detail)
                .price(price)
                .categories(categories)
                .optionGroups(optionDTOList)
                .images(images)
                .tags(tags)
                .rating(rating)
                .build();

    }

    @Transactional
    public Products updateProducts(ProductsRequest request, Long id) {

        Products products = productRepository.findById(id).orElseThrow(() -> new NotFoundResourceException(ErrorCode.RESOURCE_NOT_FOUND));
        Brands brands = brandsRepository.findById(request.getBrandId()).orElseThrow(() -> new IllegalArgumentException("not found brand"));
        Sellers sellers = sellerRepository.findById(request.getSellerId()).orElseThrow(() -> new IllegalArgumentException("not found seller"));
        products.updateFrom(request,brands,sellers);


        //detail
        DetailDTO detail = request.getDetail();

        ProductDetails.builder()
                .products(products)
                .weight(detail.getWeight())
                .dimensions(objectMapper.valueToTree(detail.getDimensions()))
                .careInstructions(detail.getCareInstructions())
                .materials(detail.getMaterials())
                .warrantyInfo(detail.getWarrantyInfo())
                .additionalInfo(objectMapper.valueToTree(detail.getAdditionalInfo()))
                .build();

        //categories
        List<CategoryRequest> categories = request.getCategories();
        List<ProductCategories> productCategories = categories.stream().map(i -> ProductCategories.builder()
                .products(products)
                .categories(categoriesRepository.findById(i.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("not found category")))
                .isPrimary(i.isPrimary())
                .build()).collect(Collectors.toList());

        products.setProductCategories(productCategories);

        //price
        PriceDTO price = request.getPrice();
        ProductPrices productPrices = ProductPrices.builder()
                .products(products)
                .basePrice(price.getBasePrice())
                .salePrice(price.getSalePrice())
                .costPrice(price.getCostPrice())
                .currency(price.getCurrency())
                .tax_rate(price.getTax_rate())
                .build();

        products.setProductPrices(productPrices);

        return products;
    }

    public void deleteProducts(Long id) {
        Products products = productRepository.findById(id).orElseThrow(() -> new NotFoundResourceException(ErrorCode.RESOURCE_NOT_FOUND));

        products.getProductImages().stream().forEach(images -> images.setProducts(null));
        products.getProductImages().clear();

        products.getProductCategories().forEach(productCategories -> productCategories.setProducts(null));
        products.getProductCategories().clear();

        ProductDetails productDetails = products.getProductDetails();
        if (productDetails != null && productDetails.getId() != null) {
            productDetails.setProducts(null);
            products.setProductDetails(null);
        }

        ProductPrices productPrices = products.getProductPrices();
        if (productPrices != null && productPrices.getId() != null) {
            productPrices.setProducts(null);
            products.setProductPrices(null);
        }

        products.getProductTags().forEach(productTags -> productTags.setProducts(null));
        products.getProductTags().clear();

        products.getProductOptionGroups().forEach(productOptionGroup -> productOptionGroup.setProducts(null));
        products.getProductOptionGroups().clear();

        List<Reviews> reviews = products.getReviews();
        if(!reviews.isEmpty()){
            reviews.stream().filter(i->i.getId() != null)
                    .forEach(review->review.setProducts(null));
            products.getReviews().clear();
        }



        Brands brands = products.getBrands();
        if (brands != null) {
            products.setBrands(null);
        }

        Sellers sellers = products.getSellers();
        if (sellers != null) {
            products.setSellers(null);
        }

        productRepository.delete(products);
    }
}
