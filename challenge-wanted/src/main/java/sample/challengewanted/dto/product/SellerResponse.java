package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerResponse {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
    private String contactEmail;
    private String contactPhone;

    @QueryProjection
    public SellerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @QueryProjection
    public SellerResponse(Long id, String name, String description, String logoUrl,
                          Double rating, String contactEmail, String contactPhone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.rating = rating;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
}