package cqrs.precourse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cqrs.precourse.domain.Product;
import cqrs.precourse.dto.ProductDto;
import cqrs.precourse.response.Response;
import cqrs.precourse.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

import static cqrs.precourse.dto.ProductDto.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Response<ProductCreateResponseDto> createProduct(@RequestBody ProductCreateRequestDto requestDto) throws JsonProcessingException {
        return Response.success(productService.createProduct(requestDto), "상품이 성공적으로 등록되었습니다.");
    }

    @GetMapping
    public void getProducts() {

    }

    @GetMapping("/{id}")
    public void getProduct() {}

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
