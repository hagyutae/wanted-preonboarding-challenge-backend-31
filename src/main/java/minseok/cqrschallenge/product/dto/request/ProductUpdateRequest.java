package minseok.cqrschallenge.product.dto.request;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;
    
    @Valid
    private ProductDetailRequest detail;
    
    @Valid
    private ProductPriceRequest price;
    
    @Valid
    private List<ProductCategoryRequest> categories;
}

