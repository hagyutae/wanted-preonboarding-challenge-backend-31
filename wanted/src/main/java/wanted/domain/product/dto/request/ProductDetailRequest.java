package wanted.domain.product.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDetailRequest(
        @PositiveOrZero(message = "무게는 0 이상이어야 합니다.")
        BigDecimal weight,

        @Valid
        Dimensions dimensions,

        @Size(max = 255, message = "재료 정보는 255자 이하로 입력해주세요.")
        String materials,

        @Size(max = 100, message = "원산지는 100자 이하로 입력해주세요.")
        String countryOfOrigin,

        @Size(max = 1000, message = "보증 정보는 1000자 이하로 입력해주세요.")
        String warrantyInfo,

        @Size(max = 1000, message = "관리 방법은 1000자 이하로 입력해주세요.")
        String careInstructions,

        @Valid
        AdditionalInfo additionalInfo
) {
        public record Dimensions(
                @Min(value = 0, message = "너비는 0 이상이어야 합니다.")
                Integer width,

                @Min(value = 0, message = "높이는 0 이상이어야 합니다.")
                Integer height,

                @Min(value = 0, message = "깊이는 0 이상이어야 합니다.")
                Integer depth
        ) {}

        public record AdditionalInfo(
                Boolean assemblyRequired,

                @Size(max = 50, message = "조립 시간은 50자 이하로 입력해주세요.")
                String assemblyTime
        ) {}
}
