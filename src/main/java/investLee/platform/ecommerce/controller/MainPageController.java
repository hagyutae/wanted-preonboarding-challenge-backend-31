package investLee.platform.ecommerce.controller;

import investLee.platform.ecommerce.dto.response.MainPageResponse;
import investLee.platform.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainPageController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<MainPageResponse> getMainPageProducts() {
        return ResponseEntity.ok(productService.getMainPageProducts());
    }
}