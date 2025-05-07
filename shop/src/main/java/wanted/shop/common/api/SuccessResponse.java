package wanted.shop.common.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({ "success", "data", "message" })
public class SuccessResponse<T> {
    private final boolean success = true;
    private final T data;
    private final String message = "요청이 성공적으로 처리되었습니다";

    public SuccessResponse(T data) {
        this.data = data;
    }

}
