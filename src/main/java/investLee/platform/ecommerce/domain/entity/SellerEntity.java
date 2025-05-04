package investLee.platform.ecommerce.domain.entity;

import investLee.platform.ecommerce.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private Double rating;

    private String contactEmail;

    private String contactPhone;

    @OneToMany(mappedBy = "seller")
    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();
}