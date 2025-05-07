package wanted.domain.seller.dto.response;

import wanted.domain.seller.entity.Seller;

import java.math.BigDecimal;

public record SellerResponse(
        Long id,
        String name,
        String description,
        String logoUrl,
        BigDecimal rating,
        String contactEmail,
        String contactPhone
) {
    public static SellerResponse of(Seller seller) {
        return new SellerResponse(
                seller.getId(),
                seller.getName(),
                seller.getDescription(),
                seller.getLogoUrl(),
                seller.getRating(),
                seller.getContactEmail(),
                seller.getContactPhone()
        );
    }
}
