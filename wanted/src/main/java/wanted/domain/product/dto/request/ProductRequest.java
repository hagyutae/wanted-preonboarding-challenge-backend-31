package wanted.domain.product.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProductRequest(
        @NotNull(message = "상품명은 필수 항목입니다.")
        @Size(max = 255, message = "상품명은 255자 이하로 입력해주세요.")
        String name,

        @NotNull(message = "slug는 필수 항목입니다.")
        @Size(max = 255, message = "slug는 255자 이하로 입력해주세요.")
        String slug,

        @Size(max = 500, message = "짧은 설명은 500자 이하로 입력해주세요.")
        String shortDescription,

        String fullDescription,

        @NotNull(message = "판매자 ID는 필수입니다.")
        Long sellerId,

        @NotNull(message = "브랜드 ID는 필수입니다.")
        Long brandId,

        @NotBlank(message = "상품 상태는 필수입니다.")
        String status,

        @Valid
        @NotNull(message = "상품 상세 정보는 필수입니다.")
        ProductDetailRequest detail,

        @Valid
        @NotNull(message = "상품 가격 정보는 필수입니다.")
        ProductPriceRequest price,

        @Valid
        @NotNull(message = "카테고리 목록은 필수입니다.")
        List<ProductCategoryRequest> categories,

        @Valid
        @NotNull(message = "옵션 그룹 목록은 필수입니다.")
        List<ProductOptionGroupRequest> optionGroups,

        @Valid
        List<ProductImageRequest> images,

        List<@NotNull(message = "태그 ID는 null일 수 없습니다.") Long> tags
) {
}
