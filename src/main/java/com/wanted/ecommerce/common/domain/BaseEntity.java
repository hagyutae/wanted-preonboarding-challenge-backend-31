package com.wanted.ecommerce.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}
