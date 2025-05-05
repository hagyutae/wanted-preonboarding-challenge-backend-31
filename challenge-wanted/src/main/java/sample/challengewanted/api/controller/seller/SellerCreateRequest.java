package sample.challengewanted.api.controller.seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SellerCreateRequest {

    private Long id;
    private String description;
    private String logoUrl;
    private Integer rating;

    @Builder
    private SellerCreateRequest(Long id, String description, String logoUrl, Integer rating) {
        this.id = id;
        this.description = description;
        this.logoUrl = logoUrl;
        this.rating = rating;
    }



}
