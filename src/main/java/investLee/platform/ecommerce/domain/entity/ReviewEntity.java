package investLee.platform.ecommerce.domain.entity;

import investLee.platform.ecommerce.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String title;
    private String content;

    private boolean verifiedPurchase;
    private int helpfulVotes;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    public void update(String title, String content, int rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

    public void incrementHelpfulVotes() {
        this.helpfulVotes += 1;
    }
}