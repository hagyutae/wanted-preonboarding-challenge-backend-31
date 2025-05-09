package cqrs.precourse.service;

import cqrs.precourse.converter.JsonbConverter;
import cqrs.precourse.domain.*;
import cqrs.precourse.dto.ProductDto;
import cqrs.precourse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static cqrs.precourse.dto.ProductDto.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private JsonbConverter jsonbConverter;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;


    public ProductCreateResponseDto createProduct(ProductCreateRequestDto requestDto) {

        // seller 찾기
        Seller seller = sellerRepository.findById(requestDto.sellerId())
                .orElseThrow(()-> new IllegalArgumentException("Invalid seller"));

        // brand 찾기
        Brand brand = brandRepository.findById(requestDto.brandId())
                .orElseThrow(()-> new IllegalArgumentException("Invalid brand"));

        // product 저장
        Product product = Product.builder()
                .name(requestDto.name())
                .slug(requestDto.slug())
                .shortDescription(requestDto.shortDescription())
                .fullDescription(requestDto.fullDescription())
                .seller(seller)
                .brand(brand)
                .build();

        productRepository.save(product);

        // productDetail
        // dimentions 맵으로 변환
        Map<String, Object> dimensions = new HashMap<>();
        dimensions.put("width", requestDto.detail().dimensions().width());
        dimensions.put("height", requestDto.detail().dimensions().height());
        dimensions.put("depth", requestDto.detail().dimensions().depth());

        // additionalInfo 맵으로 변환
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("assemblyRequired", requestDto.detail().additionalInfo().assemblyRequired());
        additionalInfo.put("assemblyTime", requestDto.detail().additionalInfo().assemblyTime());

        /**
         * public AdditionalInfoDto toDto(Map<String, Object> map) {
         *     return new AdditionalInfoDto(
         *         (Boolean) map.get("assembly_required"),
         *         (String) map.get("assembly_time")
         *     );
         * }
         */

        // productDetail저장
        ProductDetail productDetail = ProductDetail.builder()
                .product(product)
                .weight(requestDto.detail().weight())
                .dimensions(dimensions)
                .materials(requestDto.detail().materials())
                .countryOfOrigin(requestDto.detail().countryOfOrigin())
                .careInstructions(requestDto.detail().careInstructions())
                .additionalInfo(additionalInfo)
                .build();

        productDetailRepository.save(productDetail);

        // productPrice 저장
        ProductPrice productPrice = ProductPrice.builder()
                .product(product)
                .basePrice(requestDto.price().basePrice())
                .salePrice(requestDto.price().salePrice())
                .costPrice(requestDto.price().costPrice())
                .currency(requestDto.price().currency())
                .taxRate(requestDto.price().taxRate())
                .build();

        productPriceRepository.save(productPrice);

        // productCategory 저장 list(category 연동)
        for (CategoryDto dto : requestDto.categories()) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(()-> new IllegalArgumentException("Invalid category"));

            ProductCategory productCategory = ProductCategory.builder()
                    .product(product)
                    .category(category)
                    .isPrimary(dto.isPrimary())
                    .build();

            productCategoryRepository.save(productCategory);
        }

        // productOptionGroup 저장
        for (OptionGroupDto optionGroupDto : requestDto.optionGroup()) {
            ProductOptionGroup productOptionGroup = ProductOptionGroup.builder()
                    .product(product)
                    .name(optionGroupDto.name())
                    .displayOrder(optionGroupDto.displayOrder())
                    .build();

            productOptionGroupRepository.save(productOptionGroup);

            // priductOption 저장 list
            for (OptionDto optionDto : optionGroupDto.options()) {
                ProductOption productOption = ProductOption.builder()
                        .productOptionGroup(productOptionGroup)
                        .name(optionDto.name())
                        .additionalPrice(optionDto.additionalPrice())
                        .sku(optionDto.sku())
                        .stock(optionDto.stock())
                        .displayOrder(optionGroupDto.displayOrder())
                        .build();

                productOptionRepository.save(productOption);

            }
        }

        // productImage 저장
        for (ImagesDto dto : requestDto.images()) {
            ProductOption productOption = productOptionRepository.findById(dto.optionId())
                    .orElse(null);

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .url(dto.url())
                    .altText(dto.altText())
                    .isPrimary(dto.isPrimary())
                    .displayOrder(dto.displayOrder())
                    .productOption(productOption)
                    .build();

            productImageRepository.save(productImage);
        }

        // productTag 저장 Tag와 연동
        for (Long tagDto : requestDto.tags()) {
            Tag tag = tagRepository.findById(tagDto)
                    .orElseThrow(()-> new IllegalArgumentException("Invalid tag"));

            ProductTag productTag = ProductTag.builder()
                    .product(product)
                    .tag(tag)
                    .build();

            productTagRepository.save(productTag);
        }


        return ProductCreateResponseDto.of(product);
    }
}
