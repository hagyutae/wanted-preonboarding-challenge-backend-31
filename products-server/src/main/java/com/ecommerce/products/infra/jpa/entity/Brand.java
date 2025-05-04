package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    @Comment("브랜드 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("브랜드명")
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Comment("URL 슬러그")
    @Size(max = 100)
    @NotNull
    @Column(name = "slug", nullable = false, length = 100, unique = true)
    private String slug;

    @Comment("설명")
    @Lob
    @Column(name = "description")
    private String description;

    @Comment("로고 이미지 URL")
    @Size(max = 255)
    @Column(name = "logo_url")
    private String logoUrl;

    @Comment("웹사이트 URL")
    @Size(max = 255)
    @Column(name = "website")
    private String website;
}