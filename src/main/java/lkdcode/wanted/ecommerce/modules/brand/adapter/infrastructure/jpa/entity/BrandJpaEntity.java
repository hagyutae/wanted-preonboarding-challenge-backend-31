package lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "brands")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull()
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull()
    @Size(max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String slug;

    @Column(columnDefinition = "text")
    private String description;

    @Size(max = 255)
    @Column(name = "logo_url")
    private String logoUrl;

    @Size(max = 255)
    @Column(name = "website")
    private String website;

    @Builder
    public BrandJpaEntity(Long id, String name, String slug, String description, String logoUrl, String website) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.logoUrl = logoUrl;
        this.website = website;
    }
}