package minseok.cqrschallenge.product.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductUpdateRequest;
import minseok.cqrschallenge.product.dto.response.ProductDetailResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductStatus;
import minseok.cqrschallenge.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDetailResponse createProduct(ProductCreateRequest request) {
        Product product = convertToEntity(request);
        Product savedProduct = productRepository.save(product);
        return convertToDetailResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<ProductListResponse> getProducts(
            int page, int perPage, String sort, String status,
            Integer minPrice, Integer maxPrice, String category,
            Integer seller, Integer brand, Boolean inStock, String search) {
        
        Pageable pageable = createPageable(page, perPage, sort);
        
        Page<Product> productPage = productRepository.findWithFilters(
                status, minPrice, maxPrice, category, seller, brand, inStock, search, pageable);
        
        List<ProductListResponse> productResponses = productPage.getContent().stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
        
        return PaginationResponse.<ProductListResponse>builder()
                .items(productResponses)
                .pagination(PaginationResponse.Pagination.builder()
                        .totalItems(productPage.getTotalElements())
                        .totalPages(productPage.getTotalPages())
                        .currentPage(page)
                        .perPage(perPage)
                        .build())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));
        
        return convertToDetailResponse(product);
    }

    @Override
    @Transactional
    public ProductDetailResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));

        product.update(
                request.getName(),
                request.getSlug(),
                request.getShortDescription(),
                request.getFullDescription(),
                ProductStatus.valueOf(request.getStatus())
        );
        return convertToDetailResponse(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));
        
       product.delete();
    }
    
    private Pageable createPageable(int page, int perPage, String sort) {
        String[] sortParts = sort.split(":");
        String sortField = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc") 
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        return PageRequest.of(page - 1, perPage, Sort.by(direction, sortField));
    }
    
    private Product convertToEntity(ProductCreateRequest request) {
        return Product.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .status(ProductStatus.valueOf(request.getStatus()))
                .build();
    }
    

    private ProductListResponse convertToListResponse(Product product) {
        return ProductListResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .basePrice(product.getPrice().getBasePrice())
                .salePrice(product.getPrice().getSalePrice())
                .currency(product.getPrice().getCurrency())
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .build();
    }
    
    private ProductDetailResponse convertToDetailResponse(Product product) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}