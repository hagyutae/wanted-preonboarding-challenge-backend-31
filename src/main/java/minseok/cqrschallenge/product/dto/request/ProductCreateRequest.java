package minseok.cqrschallenge.product.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "상품명은 필수 항목입니다.")
    private String name;

    private String slug;

    private String shortDescription;

    private String fullDescription;

    @NotNull(message = "판매자 ID는 필수 항목입니다.")
    private Long sellerId;

    @NotNull(message = "브랜드 ID는 필수 항목입니다.")
    private Long brandId;

    @NotBlank(message = "상태는 필수 항목입니다.")
    private String status;

    @Valid
    @NotNull(message = "상품 상세 정보는 필수 항목입니다.")
    private ProductDetailRequest detail;

    @Valid
    @NotNull(message = "가격 정보는 필수 항목입니다.")
    private ProductPriceRequest price;

    @Valid
    @NotEmpty(message = "최소 하나의 카테고리를 지정해야 합니다.")
    private List<ProductCategoryRequest> categories;

    @Valid
    private List<ProductOptionGroupRequest> optionGroups;

    @Valid
    private List<ProductImageRequest> images;

    private List<Long> tags;
}
