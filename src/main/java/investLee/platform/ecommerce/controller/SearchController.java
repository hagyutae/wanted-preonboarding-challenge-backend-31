package investLee.platform.ecommerce.controller;

import investLee.platform.ecommerce.dto.request.ProductSearchRequest;
import investLee.platform.ecommerce.dto.response.ProductSummaryResponse;
import investLee.platform.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> searchByKeyword(@Valid ProductSearchRequest dto) {
        Page<ProductSummaryResponse> result = productService.searchByKeyword(dto);
        return ResponseEntity.ok(result);
    }
}