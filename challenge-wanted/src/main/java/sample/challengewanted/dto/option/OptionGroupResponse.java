package sample.challengewanted.dto.option;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OptionGroupResponse {

    private Long id;
    private String name;
    private Integer displayOrder;
    private List<OptionResponse> options;

    @QueryProjection
    public OptionGroupResponse(Long id, String name, Integer displayOrder, List<OptionResponse> options) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.options = options;
    }
}
