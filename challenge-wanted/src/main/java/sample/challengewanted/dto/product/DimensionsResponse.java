package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DimensionsResponse {
    private Integer width;
    private Integer height;
    private Integer depth;

    @QueryProjection
    public DimensionsResponse(Integer width, Integer height, Integer depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
}