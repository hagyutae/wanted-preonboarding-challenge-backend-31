package com.preonboarding.service.product;

import com.preonboarding.domain.*;
import com.preonboarding.dto.request.*;
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
    public BaseResponse<ProductResponse> editProduct(Long id, ProductEditRequestDto dto,List<ProductCategory> productCategoryList,List<ProductTag> productTagList
                                                     ,Seller seller,Brand brand) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(false,ErrorResponseDto.of(ErrorCode.PRODUCT_NOT_FOUND)));
        product.updateProduct(dto);
        product.updateSeller(seller);
        product.updateBrand(brand);

        productCategoryList.forEach(productCategory -> productCategory.updateProduct(product));
        product.updateProductCategoryList(productCategoryList);
        productTagList.forEach(productTag -> productTag.updateProduct(product));
        product.updateProductTagList(productTagList);

        ProductDetail productDetail = ProductDetail.of(dto.getDetail());
        productDetail.updateProduct(product);

        ProductPrice productPrice = ProductPrice.of(dto.getPrice());
        productPrice.updateProduct(product);

        List<ProductImage> productImageList = createProductImageList(dto.getImages(),product);
        product.updateProductImageList(productImageList);

        List<ProductOptionGroup> productOptionGroupList = createProductOptionGroup(dto.getOptionGroups(),product);
        product.updateProductOptionGroup(productOptionGroupList);

        return BaseResponse.<ProductResponse>builder()
                .success(true)
                .data(ProductResponse.createdOf(product))
                .message("상품이 성공적으로 수정되었습니다.")
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

    private List<ProductOptionGroup> createProductOptionGroup(List<ProductOptionGroupRequestDto> dtoList, Product product) {
        List<ProductOptionGroup> productOptionGroupList = new ArrayList<>();

        for (ProductOptionGroupRequestDto productOptionGroupRequestDto : dtoList) {
            ProductOptionGroup productOptionGroup = ProductOptionGroup.from(product,productOptionGroupRequestDto);

            for (ProductOptionRequestDto productOptionRequestDto : productOptionGroupRequestDto.getOptions()) {
                ProductOption productOption = ProductOption.of(productOptionRequestDto);
                productOption.updateProductOptionGroup(productOptionGroup);
            }

            productOptionGroup.updateProduct(product);
            productOptionGroupList.add(productOptionGroup);
        }

        return productOptionGroupList;
    }

    private List<ProductImage> createProductImageList(List<ProductImageRequestDto> dtoList,Product product) {
        List<ProductImage> productImageList = new ArrayList<>();

        for (ProductImageRequestDto productImageRequestDto : dtoList) {
            ProductImage productImage = ProductImage.of(productImageRequestDto);
            productImage.updateProduct(product);
            productImageList.add(productImage);
        }

        return productImageList;
    }
}
