package investLee.platform.ecommerce.dto.request;

import lombok.Getter;

@Getter
public class ImageCreateRequest {
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId; // nullable
}
