package investLee.platform.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageCreateRequest {
    @NotBlank private String url;
    private String altText;
    private boolean isPrimary;
    private int displayOrder;
    private Long optionId; // null 허용
}