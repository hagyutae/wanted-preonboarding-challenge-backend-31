package wanted.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductImageRequest(
        @NotBlank(message = "이미지 URL은 필수입니다.")
        @Size(max = 255, message = "이미지 URL은 255자 이하로 입력해주세요.")
        String url,

        @Size(max = 255, message = "대체 텍스트는 255자 이하로 입력해주세요.")
        String altText,

        Boolean isPrimary,

        @Min(value = 0, message = "표시 순서는 0 이상이어야 합니다.")
        Integer displayOrder,

        Long optionId
) {}

