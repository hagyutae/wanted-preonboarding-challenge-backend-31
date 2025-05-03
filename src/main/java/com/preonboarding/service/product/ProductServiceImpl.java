package com.preonboarding.service.product;

import com.preonboarding.domain.*;
import com.preonboarding.dto.request.ProductCreateRequestDto;
import com.preonboarding.dto.request.ProductImageRequestDto;
import com.preonboarding.dto.request.ProductOptionGroupRequestDto;
import com.preonboarding.dto.request.ProductOptionRequestDto;
import com.preonboarding.dto.response.ProductResponse;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public BaseResponse<ProductResponse> createProduct(ProductCreateRequestDto dto,List<ProductCategory> productCategoryList,List<ProductTag> productTagList
            ,Seller seller,Brand brand) {
        Product product = Product.from(seller,brand,dto);

        productCategoryList.forEach(productCategory -> productCategory.updateProduct(product));
        productTagList.forEach(productTag -> productTag.updateProduct(product));

        ProductDetail productDetail = ProductDetail.of(dto.getDetail());
        productDetail.updateProduct(product);

        ProductPrice productPrice = ProductPrice.of(dto.getPrice());
        productPrice.updateProduct(product);

        createProductOptionGroup(dto.getOptionGroups(),product);
        createProductImageList(dto.getImages(),product);

        productRepository.save(product);

        return BaseResponse.<ProductResponse>builder()
                .success(true)
                .message("상품이 성공적으로 등록되었습니다.")
                .data(ProductResponse.of(product))
                .build();
    }

    @Override
    @Transactional
    public BaseResponse<ProductResponse> deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.PRODUCT_NOT_FOUND)));
        product.deleteProduct();

        return BaseResponse.<ProductResponse>builder()
                .success(true)
                .data(null)
                .message("상품이 성공적으로 삭제되었습니다.")
                .build();
    }

    private void createProductOptionGroup(List<ProductOptionGroupRequestDto> dtoList, Product product) {
        for (ProductOptionGroupRequestDto productOptionGroupRequestDto : dtoList) {
            ProductOptionGroup productOptionGroup = ProductOptionGroup.from(product,productOptionGroupRequestDto);

            for (ProductOptionRequestDto productOptionRequestDto : productOptionGroupRequestDto.getOptions()) {
                ProductOption productOption = ProductOption.of(productOptionRequestDto);
                productOption.updateProductOptionGroup(productOptionGroup);
            }

            productOptionGroup.updateProduct(product);
        }
    }

    private void createProductImageList(List<ProductImageRequestDto> dtoList,Product product) {
        for (ProductImageRequestDto productImageRequestDto : dtoList) {
            ProductImage productImage = ProductImage.of(productImageRequestDto);
            productImage.updateProduct(product);
        }
    }
}
