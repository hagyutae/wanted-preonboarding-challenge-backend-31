package sample.challengewanted.dto.option;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class OptionResponse {

    private Long id;
    private String name;
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;

    @QueryProjection
    public OptionResponse(Long id, String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {
        this.id = id;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku = sku;
        this.stock = stock;
        this.displayOrder = displayOrder;
    }


}
