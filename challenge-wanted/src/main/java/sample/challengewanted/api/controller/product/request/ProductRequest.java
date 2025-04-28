package sample.challengewanted.api.controller.product.request;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class ProductRequest {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;

    @Builder
    private ProductRequest(Long id, String name, String slug, String shortDescription, String fullDescription, Long sellerId, Long brandId, String status) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.sellerId = sellerId;
        this.brandId = brandId;
        this.status = status;
    }
}
