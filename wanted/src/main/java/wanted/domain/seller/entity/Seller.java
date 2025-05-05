package wanted.domain.seller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.common.entity.BaseCreateEntity;

import java.math.BigDecimal;

@Entity(name = "sellers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Seller extends BaseCreateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String logoUrl;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    private String contactEmail;

    private String contactPhone;
}
