package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponse {

    private Long id;
    private String url;
    private String altText;
    private boolean isPrimary;
    private Integer displayOrder;
    private Long optionId;

    @QueryProjection
    public ImageResponse(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }

    @QueryProjection
    public ImageResponse(Long id, String url, String altText, boolean isPrimary, Integer displayOrder, Long optionId) {
        this.id = id;
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.optionId = optionId;
    }
}
