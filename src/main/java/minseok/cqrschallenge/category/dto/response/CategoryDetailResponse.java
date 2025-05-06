package minseok.cqrschallenge.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    private String imageUrl;
    private CategoryParentResponse parent;
}