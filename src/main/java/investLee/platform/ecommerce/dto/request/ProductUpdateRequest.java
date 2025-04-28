package investLee.platform.ecommerce.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductUpdateRequest {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private String status;
    private ProductCreateRequest.ProductDetailDto detail;
    private ProductCreateRequest.ProductPriceDto price;
    private List<ProductCreateRequest.OptionGroupDto> optionGroups;
    private List<ProductCreateRequest.ProductImageDto> images;
}