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
    private String description;
    private String logoUrl;
    private String website;

    @QueryProjection
    public BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @QueryProjection
    public BrandResponse(Long id, String name, String description, String logoUrl, String website) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.website = website;
    }
}
