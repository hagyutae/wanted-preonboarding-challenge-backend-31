package wanted.shop.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class TestUser {

    @Size(min = 5, message = "이름은 5자 이상 입력해주세요")
    @NotBlank(message = "이름은 필수 항목입니다")
    private String name;

    @JsonProperty("password")
    @NotBlank(message = "비밀번호는 필수 항목입니다")
    private String pwd;
}
