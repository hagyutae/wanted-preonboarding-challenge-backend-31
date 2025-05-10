package com.wanted.ecommerce.category.dto.request;

import com.wanted.ecommerce.common.utils.SortUtils;
import jakarta.validation.constraints.Min;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableRequest {
    @Builder.Default
    @Min(value = 1)
    private Integer page = 1;
    @Builder.Default
    @Min(value = 10)
    private Integer perPage = 10;
    @Builder.Default
    private String sort = "created_at:desc";

    public Map<String, String> getSort(){
        return SortUtils.createSortMap(sort);
    }
}
