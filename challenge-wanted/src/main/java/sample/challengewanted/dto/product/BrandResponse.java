package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.brand.Brand;

@Getter
@NoArgsConstructor
public class BrandResponse {

    private Long id;
    private String name;

    @QueryProjection
    public BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
