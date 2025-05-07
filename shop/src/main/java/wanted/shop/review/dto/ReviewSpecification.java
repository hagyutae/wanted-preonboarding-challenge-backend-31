package wanted.shop.review.dto;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import wanted.shop.review.domain.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewSpecification {

    public static Specification<Review> withFilters(ProductId productId, Rating rating) {
        return (root, query, cb) -> {

            List<Predicate> predicates = List.of(
                    cb.equal(
                            root.get(Review_.productId).get(ProductId_.id),
                            productId.getId()
                    ),
                    cb.equal(
                            root.get(Review_.data).get(ReviewData_.RATING),
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
                            root.get(Review_.productId).get(ProductId_.id),
                            productId.getId()
                    )
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
