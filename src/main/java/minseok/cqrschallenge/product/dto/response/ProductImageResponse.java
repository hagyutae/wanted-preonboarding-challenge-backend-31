package minseok.cqrschallenge.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageResponse {

    private Long id;

    private String url;

    private String altText;

    private Boolean isPrimary;

    private Integer displayOrder;

    private Long optionId;
}