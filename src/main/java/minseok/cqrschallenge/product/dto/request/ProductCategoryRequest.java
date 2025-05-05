package minseok.cqrschallenge.product.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryRequest {
    @NotNull(message = "카테고리 ID는 필수 항목입니다.")
    private Long categoryId;
    
    private Boolean isPrimary;
}