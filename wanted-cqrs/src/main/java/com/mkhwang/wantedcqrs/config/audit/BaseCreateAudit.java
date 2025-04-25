package com.mkhwang.wantedcqrs.config.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class BaseCreateAudit {

  @Column(updatable = false)
  @CreatedDate
  private Instant createdAt;

}
