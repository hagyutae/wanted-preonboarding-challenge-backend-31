package investLee.platform.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequest {

    @NotNull
    private Integer rating;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long userId; // 검증용 (실제 인증시 제외 가능)
}
