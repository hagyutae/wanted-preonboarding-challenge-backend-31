package wanted.shop.test;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.shop.common.annotation.ValidationFailureMessage;
import wanted.shop.common.api.ErrorCode;
import wanted.shop.common.api.PaginatedData;
import wanted.shop.common.api.Pagination;
import wanted.shop.common.api.SuccessResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/ping")
    public SuccessResponse<String> ping() {
        return new SuccessResponse<>("ping");
    }

    @GetMapping("/pings")
    public SuccessResponse<PaginatedData<String>> pings() {
        Pagination pagination = new Pagination(1, 2, 3, 4);
        List<String> list = Arrays.asList("a", "b", "c", "d");
        PaginatedData<String> data = new PaginatedData<>(list, pagination);
        return new SuccessResponse<>(data);
    }

    @GetMapping("/error")
    public SuccessResponse<String> error() throws Exception {
        throw new Exception("ping 테스트 중 예외 발생");
    }

    @ValidationFailureMessage("상품 등록에 실패했습니다.")
    @GetMapping("/error_detail")
    public SuccessResponse<TestUser> errorDetail(@ModelAttribute @Valid TestUser user) throws Exception {
        return new SuccessResponse<>(user);
    }

}
