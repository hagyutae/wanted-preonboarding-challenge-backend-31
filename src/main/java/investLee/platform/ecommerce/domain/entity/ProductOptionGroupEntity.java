package investLee.platform.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product_option_group")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String name;
    private Integer displayOrder;

    @OneToMany(mappedBy = "optionGroup")
    private List<ProductOptionEntity> options;
}
