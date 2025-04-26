package sample.challengewanted.domain.review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.BaseEntity;
import sample.challengewanted.domain.product.entity.ProductDetail;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
@Entity
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;
    private String title;
    private String content;
    private boolean verifiedPurchase;
    private Integer helpfulVotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductDetail productDetail;

    public void assingProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}
