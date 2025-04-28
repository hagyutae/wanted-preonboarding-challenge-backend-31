package sample.challengewanted.api.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sample.challengewanted.api.controller.category.request.CategoryRequest;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ProductCreateRequest {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;

    private ProductDetailRequest detail;
    private ProductPriceRequest price;
    private List<CategoryRequest> categories;
    private List<OptionGroupRequest> optionGroups;
    private List<ImageRequest> images;
    private List<Long> tags;

}
