package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.seller.Seller;

@Getter
@NoArgsConstructor
public class SellerResponse {

    private Long id;
    private String name;

    @QueryProjection
    public SellerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
