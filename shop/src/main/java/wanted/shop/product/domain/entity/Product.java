package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.seller.domain.entity.Seller;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "products_id_seq", allocationSize = 1)
    private Long id;

    public ProductId getId() {
        return new ProductId(this.id);
    }

    @Embedded
    private ProductData productData;

    private String status;

    @Embedded
    private ProductTimestamps productTimestamps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductDetail productDetail;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductPrice productPrice;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionGroup> optionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTag> tags = new ArrayList<>();

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public void setProductPrice(ProductPrice price) {
        this.productPrice = price;
    }
    public void addTags(List<ProductTag> tags) {
        this.tags.addAll(tags);
    }

    public void addImages(List<ProductImage> images) {
        this.images.addAll(images);
    }

    public void addOptionGroups(List<ProductOptionGroup> groups) {
        this.optionGroups.addAll(groups);
    }

    public void addProductCategories(List<ProductCategory> groups) {
        this.productCategories.addAll(groups);
    }

}

