package sample.challengewanted.api.controller.category.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CategoryRequest {
    private Long categoryId;
    private boolean isPrimary;

    @Builder
    private CategoryRequest(Long categoryId, boolean isPrimary) {
        this.categoryId = categoryId;
        this.isPrimary = isPrimary;
    }
}
