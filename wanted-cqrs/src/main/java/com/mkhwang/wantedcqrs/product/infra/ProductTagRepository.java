package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.ProductTag;
import com.mkhwang.wantedcqrs.product.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
  void deleteByProductId(Long productId);

  void deleteByProductIdAndTagNotIn(Long id, List<Tag> allTags);

  List<ProductTag> findByProductId(Long id);
}
