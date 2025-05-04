package investLee.platform.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {
    @NotNull
    private Integer rating; // 1~5

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long userId; // 실제 사용자 정보로 교체 가능

    private Boolean verifiedPurchase = false;
}