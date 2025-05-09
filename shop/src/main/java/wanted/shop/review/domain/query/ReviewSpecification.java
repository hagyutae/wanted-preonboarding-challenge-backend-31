package wanted.shop.review.domain.query;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import wanted.shop.product.domain.ProductId;

import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewData_;
import wanted.shop.review.domain.entity.Review_;

import java.util.List;

public class ReviewSpecification {

    public static Specification<Review> withFilters(ProductId productId, Rating rating) {
        return (root, query, cb) -> {

            List<Predicate> predicates = List.of(
                    cb.equal(
                            root.get(Review_.productId).get("id"),
                            productId.getId()
                    ),
                    cb.equal(
                            root.get(Review_.reviewData).get(ReviewData_.RATING),
                            rating.getValue()
                    )
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Review> withFilters(ProductId productId) {
        return (root, query, cb) -> {

            List<Predicate> predicates = List.of(
                    cb.equal(
                            root.get(Review_.productId).get("id"),
                            productId.getId()
                    )
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
