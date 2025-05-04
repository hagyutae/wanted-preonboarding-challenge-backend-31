package investLee.platform.ecommerce.dto.request;

import investLee.platform.ecommerce.dto.ProductSortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchConditionRequest {

    private Long categoryId;
    private Long brandId;
    private Long sellerId;
    private List<Long> tagIds;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private Boolean inStockOnly;

    private String keyword;

    private LocalDate fromDate;
    private LocalDate toDate;

    private ProductSortType sortType = ProductSortType.NEWEST;

    private int page = 0;
    private int size = 20;
}
