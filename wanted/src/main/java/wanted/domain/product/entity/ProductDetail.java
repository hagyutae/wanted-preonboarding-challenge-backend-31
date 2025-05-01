package wanted.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "product_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    private Double weight;

    @Column(columnDefinition = "jsonb")
    private String dimensions;

    private String materials;

    private String countryOfOrigin;

    private String warrantyInfo;

    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    private String additionalInfo;
}
