package minseok.cqrschallenge.product.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionGroupRequest {

    @NotBlank(message = "옵션 그룹명은 필수 항목입니다.")
    private String name;

    private Integer displayOrder;

    @Valid
    @NotEmpty(message = "최소 하나의 옵션을 추가해야 합니다.")
    private List<ProductOptionRequest> options;
}