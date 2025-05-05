package minseok.cqrschallenge.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageCreateRequest {
    @NotBlank(message = "이미지 URL은 필수 항목입니다.")
    private String url;
    
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId;
}