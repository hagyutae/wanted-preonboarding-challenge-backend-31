package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class RatingDto {

    private Double average;
    private Integer count;
    private List<Map<String, Long>> distribution;

    @QueryProjection
    public RatingDto(Double average, Integer count, List<Map<String, Long>> distribution) {
        this.average = average;
        this.count = count;
        this.distribution = distribution;
    }
}
