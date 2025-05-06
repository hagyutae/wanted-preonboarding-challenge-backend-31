package com.pawn.wantedcqrs.brand.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "brands")
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    name: 브랜드명
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    //    slug: URL 슬러그
    @Column(name = "slug", length = 100, nullable = false)
    private String slug;

    //    description: 설명
    @Column(name = "description")
    private String description;

    //    logo_url: 로고 이미지 URL
    @Column(name = "logo_url")
    private String logoUrl;

    //    website: 웹사이트 URL
    @Column(name = "website")
    private String website;

    protected Brand(Long id, String name, String slug, String description, String logoUrl, String website) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.logoUrl = logoUrl;
        this.website = website;
    }

}
