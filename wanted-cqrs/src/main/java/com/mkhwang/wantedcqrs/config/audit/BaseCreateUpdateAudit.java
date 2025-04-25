package com.mkhwang.wantedcqrs.config.audit;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class BaseCreateUpdateAudit extends BaseCreateAudit{

  @LastModifiedDate
  private Instant updatedAt;
}
