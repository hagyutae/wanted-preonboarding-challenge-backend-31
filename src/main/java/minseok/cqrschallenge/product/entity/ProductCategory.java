package minseok.cqrschallenge.product.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.category.entity.Category;

@Entity
@Table(name = "product_categories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Builder
    public ProductCategory(Product product, Category category, Boolean isPrimary) {
        this.product = product;
        this.category = category;
        this.isPrimary = isPrimary;
    }

    public void update(Product product, Category category, Boolean isPrimary) {
        this.product = product;
        this.category = category;
        this.isPrimary = isPrimary;
    }

    public void associateCategory(Category category) {
        this.category = category;
    }

    public void associateProduct(Product product) {
        this.product = product;
    }
}