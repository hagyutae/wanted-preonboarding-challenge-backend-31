package lkdcode.wanted.ecommerce.modules.reviews.adapter.infrastructure.jpa.repository;

import lkdcode.wanted.ecommerce.modules.reviews.adapter.infrastructure.jpa.entity.ReviewJpaEntity;
import org.springframework.data.repository.Repository;

public interface ReviewJpaRepository extends Repository<ReviewJpaEntity, Long> {
}
