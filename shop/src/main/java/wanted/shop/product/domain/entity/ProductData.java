package wanted.shop.product.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class ProductData {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
}
