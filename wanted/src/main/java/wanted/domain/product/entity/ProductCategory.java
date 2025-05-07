package wanted.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.domain.category.entity.Category;
import wanted.domain.product.dto.request.ProductCategoryRequest;

@Entity
@Table(name = "product_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean isPrimary;

    @Builder
    public ProductCategory(Product product, Category category, boolean isPrimary) {
        this.product = product;
        this.category = category;
        this.isPrimary = isPrimary;
    }

    public static ProductCategory from(ProductCategoryRequest dto, Product product, Category category) {
        return ProductCategory.builder()
                .product(product)
                .category(category)
                .isPrimary(dto.isPrimary())
                .build();
    }
}
