package sample.challengewanted.api.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageRequest {
    private String url;
    private String altText;
    private boolean isPrimary;
    private Integer displayOrder;
    private Long optionId; // 옵션에 매핑되는 경우 사용

    @Builder
    private ImageRequest(String url, String altText, boolean isPrimary, Integer displayOrder, Long optionId) {
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.optionId = optionId;
    }
}