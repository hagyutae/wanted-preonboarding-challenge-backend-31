package com.sandro.wanted_shop.product;

import com.sandro.wanted_shop.brand.Brand;
import com.sandro.wanted_shop.brand.BrandRepository;
import com.sandro.wanted_shop.category.Category;
import com.sandro.wanted_shop.category.CategoryRepository;
import com.sandro.wanted_shop.product.dto.Dimensions;
import com.sandro.wanted_shop.product.entity.*;
import com.sandro.wanted_shop.product.entity.enums.ProductStatus;
import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;
import com.sandro.wanted_shop.product.persistence.ProductRepository;
import com.sandro.wanted_shop.review.entity.User;
import com.sandro.wanted_shop.seller.Seller;
import com.sandro.wanted_shop.seller.SellerRepository;
import com.sandro.wanted_shop.tag.Tag;
import com.sandro.wanted_shop.tag.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {
    @Autowired ProductService sut;
    @Autowired ProductRepository productRepository;
    @Autowired BrandRepository brandRepository;
    @Autowired SellerRepository sellerRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired TagRepository tagRepository;

    @DisplayName("상품을 등록한다.")
    @Test
    void register() throws Exception {
        Product product = Product.builder()
                .name("슈퍼 편안한 소파")
                .slug("super-comfortable-sofa")
                .shortDescription("최고급 소재로 만든 편안한 소파")
                .fullDescription("<p>이 소파는 최고급 소재로 제작되었으며...</p>")
                .status(ProductStatus.ACTIVE)
                .brand(getBrand())
                .seller(getSeller())
                .build();

        ProductPrice.builder()
                .product(product)
                .basePrice(BigDecimal.valueOf(599000.00))
                .costPrice(BigDecimal.valueOf(350000.00))
                .salePrice(BigDecimal.valueOf(499000.00))
                .taxRate(BigDecimal.valueOf(10.00))
                .build();

        ProductDetail.builder()
                .product(product)
                .additionalInfo(Map.of("assembly_time", "30분", "assembly_required", true))
                .weight(BigDecimal.valueOf(25.50))
                .dimensions(Dimensions.builder()
                        .depth(90)
                        .width(200)
                        .height(85)
                        .build())
                .careInstructions("마른 천으로 표면을 닦아주세요")
                .countryOfOrigin("대한민국")
                .warrantyInfo("2년 품질 보증")
                .materials("가죽, 목재, 폼")
                .build();

        ProductOptionGroup optionGroup = ProductOptionGroup.builder()
                .product(product)
                .name("색상")
                .build();

        ProductOption option = ProductOption.builder()
                .optionGroup(optionGroup)
                .name("브라운")
                .stock(10)
                .build();

        ProductImage.builder()
                .product(product)
                .option(option)
                .url("http://example.com/images/default.png")
                .isPrimary(true)
                .build();

        product.addCategory(getCategory(), true);
        product.addTag(getTag());

        Product registerdProduct = sut.register(product);

        Optional<Product> foundProduct = productRepository.findById(registerdProduct.getId());

        assertThat(foundProduct).isPresent();
    }

    private Category getCategory() {
        Category level2 = categoryRepository.save(Category.builder()
                .name("거실 가구")
                .slug("living-room")
                .description("거실을 위한 다양한 가구 제품")
                .level(2)
                .imageUrl("https://example.com/categories/living-room.jpg")
                .build());

        Category level1 = Category.builder()
                .name("가구")
                .slug("furniture")
                .description("편안한 생활을 위한 다양한 가구 컬렉션")
                .level(1)
                .imageUrl("https://example.com/categories/furniture.jpg")
                .build();

        level1.addChild(level2);

        return categoryRepository.save(level1);
    }

    private Tag getTag() {
        return tagRepository.save(Tag.builder()
                .name("베스트셀러")
                .slug("bestseller")
                .build());
    }

    private static User getUser() {
        return User.builder()
                .name("김지원")
                .email("jiwon.kim@example.com")
                .avatarUrl("https://example.com/avatars/jiwon.jpg")
                .build();
    }

    private Brand getBrand() {
        return brandRepository.save(Brand.builder()
                .name("편안가구")
                .slug("comfort-furniture")
                .description("편안함에 집중한 프리미엄 가구 브랜드")
                .logoUrl("https://example.com/logos/comfortfurniture.png")
                .website("https://comfortfurniture.com")
                .build());
    }

    private Seller getSeller() {
        return sellerRepository.save(Seller.builder()
                .name("홈퍼니처")
                .description("최고의 가구 전문 판매점")
                .logoUrl("https://example.com/logos/homefurniture.png")
                .rating(BigDecimal.valueOf(4.80))
                .contactEmail("contact@homefurniture.com")
                .contactPhone("02-1234-5678")
                .build());
    }
}