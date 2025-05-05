package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.product.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class RelatedProductDto {

    private Long id;
    private String name;
    private String shortDescription;
    private ImageResponse primaryImage;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String currency;

    @QueryProjection
    public RelatedProductDto(Long id, String name, String shortDescription, ImageResponse primaryImage, BigDecimal basePrice, BigDecimal salePrice, String currency) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.primaryImage = primaryImage;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.currency = currency;
    }


}
