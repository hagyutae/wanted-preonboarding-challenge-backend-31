package wanted.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.domain.product.dto.request.ProductOptionGroupRequest;

import java.util.List;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String name;

    private int displayOrder;

    @JsonIgnore
    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.REMOVE)
    private List<ProductOption> options;


    @Builder
    public ProductOptionGroup(Product product, String name, int displayOrder) {
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder;
    }

    public static ProductOptionGroup from(ProductOptionGroupRequest dto, Product product) {
        return ProductOptionGroup.builder()
                .product(product)
                .name(dto.name())
                .displayOrder(dto.displayOrder() != null ? dto.displayOrder() : 0)
                .build();
    }
}
