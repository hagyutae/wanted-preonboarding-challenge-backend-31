package com.preonboarding.service.category;

import com.preonboarding.domain.Category;
import com.preonboarding.domain.Product;
import com.preonboarding.domain.ProductCategory;
import com.preonboarding.domain.Review;
import com.preonboarding.dto.request.category.CategoryRequestDto;
import com.preonboarding.dto.response.category.CategoryResponse;
import com.preonboarding.dto.response.category.CategoryResponseWrapper;
import com.preonboarding.dto.response.product.ProductPageResponse;
import com.preonboarding.dto.response.review.ReviewSummaryResponse;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.global.response.paging.CategoryPageBaseResponse;
import com.preonboarding.global.response.paging.PaginationDto;
import com.preonboarding.repository.category.CategoryRepository;
import com.preonboarding.repository.product.ProductRepository;
import com.preonboarding.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public BaseResponse<List<CategoryResponse>> getCategories(int level) {
        List<Category> categories = categoryRepository.findCategoriesByLevel(level);

        List<CategoryResponse> categoryResponseList = categories.stream()
                .map(this::convertToCategoryResponse)
                .toList();

        return BaseResponse.<List<CategoryResponse>>builder()
                .success(true)
                .data(categoryResponseList)
                .message("카테고리 목록을 성공적으로 조회했습니다.")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryPageBaseResponse<ProductPageResponse> getProductsByCategory(Long id,Integer page,Integer perPage,String sort) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(false,ErrorResponseDto.of(ErrorCode.CATEGORY_NOT_FOUND)));
        CategoryResponse categoryResponse = CategoryResponse.of(category);
        CategoryResponseWrapper wrapper = CategoryResponseWrapper.builder().category(categoryResponse).build();

        int pageNum = PagingUtil.getPageNumber(page);
        int elementNum = PagingUtil.getPerPageCount(perPage);

        Sort sortValue = PagingUtil.getReviewPagingSort(sort);
        Pageable pageRequest = PageRequest.of(pageNum,elementNum,sortValue);

        Page<Product> productCategoryList = productRepository.findProductsWithCategoryId(pageRequest,id);

        List<ProductPageResponse> productPageResponseList = productCategoryList.stream()
                .map(product -> {
                    ReviewSummaryResponse reviewSummaryResponse = Review.createSummaryResponse(product.getReviewList());
                    return ProductPageResponse.from(product,reviewSummaryResponse);
                })
                .toList();

        PaginationDto paginationDto = PaginationDto.from(productCategoryList,pageNum,elementNum);

        return CategoryPageBaseResponse.<ProductPageResponse>builder()
                .success(true)
                .data(wrapper)
                .items(productPageResponseList)
                .pagination(paginationDto)
                .message("카테고리 상품 목록을 성공적으로 조회했습니다.")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductCategory> createProductCategories(List<CategoryRequestDto> dtoList) {
        List<ProductCategory> categories = new ArrayList<>();

        for (CategoryRequestDto requestDto : dtoList) {
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.CATEGORY_NOT_FOUND)));

            ProductCategory productCategory = ProductCategory.of(category,requestDto.isPrimary());
            categories.add(productCategory);
        }

        return categories;
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        List<CategoryResponse> childrenResponseList = category.getChildren().stream()
                .map(this::convertToCategoryResponse)
                .toList();

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .level(category.getLevel())
                .imageUrl(category.getImageUrl())
                .children(childrenResponseList)
                .build();
    }
}
