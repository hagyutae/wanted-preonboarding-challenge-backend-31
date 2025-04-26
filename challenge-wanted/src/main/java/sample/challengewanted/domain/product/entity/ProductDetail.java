package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.review.Review;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_details")
@Entity
public class ProductDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weight;

    @Column(columnDefinition = "jsonb")
    private String dimensions;

    private String material;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    private String additionalInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @OneToMany(mappedBy = "productDetail")
    List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        this.reviews.add(review);
        review.assingProductDetail(this);
    }

}
