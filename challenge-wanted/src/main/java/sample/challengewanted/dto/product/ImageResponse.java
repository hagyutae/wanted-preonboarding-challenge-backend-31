package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponse {

    private String url;

    private String altText;

    @QueryProjection
    public ImageResponse(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }
}
