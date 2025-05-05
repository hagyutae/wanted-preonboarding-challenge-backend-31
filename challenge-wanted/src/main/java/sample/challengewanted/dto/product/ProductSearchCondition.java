package sample.challengewanted.dto.product;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductSearchCondition {
    private String status;
    private Integer minPrice;
    private Integer maxPrice;
    private List<Integer> category;
    private Integer seller;
    private Integer brand;
    private Boolean inStock;
    private String search;
}
