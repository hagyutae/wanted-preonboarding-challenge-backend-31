package minseok.cqrschallenge.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedCategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String imageUrl;
    private Integer productCount;
}