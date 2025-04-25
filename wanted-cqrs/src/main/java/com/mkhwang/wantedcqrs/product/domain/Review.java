package com.mkhwang.wantedcqrs.product.domain;

import com.mkhwang.wantedcqrs.config.audit.BaseCreateUpdateAudit;
import com.mkhwang.wantedcqrs.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.hibernate.annotations.Check;

@Getter
@Entity(name = "reviews")
@Check(constraints = "rating BETWEEN 1 AND 5")
public class Review extends BaseCreateUpdateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Min(1)
  @Max(5)
  @Column(nullable = false)
  private int rating;
  private String title;
  private String content;
  @Column(name = "verified_purchase")
  private boolean verifiedPurchase;
  @Column(name = "helpful_votes")
  private int helpfulVotes = 0;
}
