package sample.challengewanted.domain.product.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.api.controller.product.request.ProductDetailRequest;
import sample.challengewanted.domain.review.Review;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_details")
@Entity
public class ProductDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weight;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String dimensions;

    private String material;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String additionalInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @OneToMany(mappedBy = "productDetail")
    List<Review> reviews = new ArrayList<>();

    private ProductDetail(ProductDetailRequest request) {
        this.weight = request.getWeight();
        this.dimensions = request.getDimensionsAsJson();
        this.material = request.getMaterials();
        this.countryOfOrigin = request.getCountryOfOrigin();
        this.warrantyInfo = request.getWarrantyInfo();
        this.careInstructions = request.getCareInstructions();
        this.additionalInfo = request.getAdditionalInfoAsJson();
    }

    public static ProductDetail create(ProductDetailRequest request) {
        return new ProductDetail(request);
    }

    public void assignProduct(Product product) {
        this.product = product;
        if (product.getProductDetail() != this) {
            product.assignProductDetail(this);
        }
    }

}
