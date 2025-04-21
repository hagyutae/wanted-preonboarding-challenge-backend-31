package investLee.platform.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    private Integer level;
    private String imageUrl;

    @OneToMany(mappedBy = "category")
    private List<ProductCategoryEntity> products;
}
