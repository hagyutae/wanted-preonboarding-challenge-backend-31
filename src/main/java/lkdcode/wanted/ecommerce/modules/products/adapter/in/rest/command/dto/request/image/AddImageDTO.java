package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.image;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.image.AddImageModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductAltText;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageIsPrimary;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageUrl;

public record AddImageDTO(

    @NotEmpty(message = "이미지 URL은 필수입니다.")
    @Size(max = ProductImageUrl.LENGTH, message = ProductImageUrl.LENGTH_ERROR_MESSAGE)
    String url,

    @Size(max = ProductAltText.LENGTH, message = ProductAltText.LENGTH_ERROR_MESSAGE)
    String altText,
    Boolean isPrimary,
    Integer displayOrder,
    Integer optionId
) {

    public AddImageModel getAddImageModel(final ProductId productId) {
        return new AddImageModel(
            productId,
            getProductImageUrl(),
            getProductAltText(),
            getProductImageIsPrimary(),
            getProductImageDisplayOrder(),
            optionId == null ? null : getProductOptionId()
        );
    }

    public ProductImageUrl getProductImageUrl() {
        return new ProductImageUrl(url);
    }

    public ProductAltText getProductAltText() {
        return new ProductAltText(altText);
    }

    public ProductImageIsPrimary getProductImageIsPrimary() {
        return new ProductImageIsPrimary(isPrimary);
    }

    public ProductImageDisplayOrder getProductImageDisplayOrder() {
        return new ProductImageDisplayOrder(displayOrder);
    }

    public ProductOptionId getProductOptionId() {
        return new ProductOptionId(optionId.longValue());
    }
}