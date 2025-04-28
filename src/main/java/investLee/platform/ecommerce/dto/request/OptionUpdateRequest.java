package investLee.platform.ecommerce.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OptionUpdateRequest {
    private String name;
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
