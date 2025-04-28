package sample.challengewanted.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
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
