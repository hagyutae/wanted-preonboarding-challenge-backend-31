package wanted.domain.product.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProductOptionGroupRequest(
        @NotBlank(message = "옵션 그룹 이름은 필수입니다.")
        @Size(max = 100, message = "옵션 그룹 이름은 100자 이하로 입력해주세요.")
        String name,

        Integer displayOrder,

        @Valid
        @NotEmpty(message = "옵션 그룹에는 하나 이상의 옵션이 포함되어야 합니다.")
        List<ProductOptionRequest> options
) {}
