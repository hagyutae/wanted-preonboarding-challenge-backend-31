package lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryJpaEntity extends EntityValidator<CategoryJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "카테고리의 이름은 100자를 초과할 수 없습니다.")
    @Column(length = 100)
    private String name;

    @NotNull(message = "슬러그는 필수입니다.")
    @Size(max = 100, message = "슬러그는 100자를 초과할 수 없습니다.")
    @Column(length = 100, unique = true, nullable = false)
    private String slug;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryJpaEntity parent;

    @NotNull(message = "level은 필수입니다.")
    @Column(nullable = false)
    private Integer level;

    @Size(max = 255, message = "imageURL은 최대 255자까지 가능합니다.")
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "parent")
    private List<CategoryJpaEntity> children = new ArrayList<>();

    @Builder
    public CategoryJpaEntity(Long id, String name, String slug, String description, CategoryJpaEntity parent, Integer level, String imageUrl, List<CategoryJpaEntity> children) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.level = level;
        this.imageUrl = imageUrl;
        this.children = children;
        super.validSelf();
    }
}