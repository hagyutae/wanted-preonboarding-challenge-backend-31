package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCategoryDto {

    private Long id;
    private String name;
    private String slug;
    private Boolean isPrimary;
    private ParentCategoryDto parent;

    @QueryProjection
    public ProductCategoryDto(Long id, String name, String slug, Boolean isPrimary, ParentCategoryDto parent) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.isPrimary = isPrimary;
        this.parent = parent;
    }
}
