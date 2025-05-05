package sample.challengewanted.api.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class OptionGroupRequest {
    private String name;
    private Integer displayOrder;
    private List<OptionRequest> options;

    @Builder
    private OptionGroupRequest(String name, Integer displayOrder, List<OptionRequest> options) {
        this.name = name;
        this.displayOrder = displayOrder;
        this.options = options;
    }

    @Getter
    @NoArgsConstructor
    public static class OptionRequest {
        private String name;
        private BigDecimal additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;

        @Builder
        private OptionRequest(String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {
            this.name = name;
            this.additionalPrice = additionalPrice;
            this.sku = sku;
            this.stock = stock;
            this.displayOrder = displayOrder;
        }
    }
}