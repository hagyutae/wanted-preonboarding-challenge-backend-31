package cqrs.precourse.controller;

import cqrs.precourse.domain.Product;
import cqrs.precourse.dto.ProductDto;
import cqrs.precourse.response.Response;
import cqrs.precourse.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

import static cqrs.precourse.dto.ProductDto.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Response<ProductCreateResponseDto> createProduct(@RequestBody ProductCreateRequestDto requestDto) {
        return Response.success(productService.createProduct(requestDto), "상품이 성공적으로 등록되었습니다.");
    }

    @GetMapping
    public List<Product> getProducts() {

    }

    @GetMapping("/{id}")
    public Product getProduct() {}

    @PutMapping("/{id}")
    public void updateProduct() {}

    @DeleteMapping("/{id}")
    public void deleteProduct() {}

    @PostMapping("/{id}/options")
    public void addOption() {}

    @PutMapping("/{id}/options/{optionId}")
    public void updateOption() {}

    @DeleteMapping("/{id}/options/{optionId}")
    public void deleteOption() {

    }

    @PostMapping("/{id}/images")
    public void addImage() {}
}
