package investLee.platform.ecommerce.controller;

import investLee.platform.ecommerce.dto.request.ProductImageCreateRequest;
import investLee.platform.ecommerce.dto.request.ProductImageUpdateRequest;
import investLee.platform.ecommerce.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService imageService;

    @PostMapping
    public ResponseEntity<Void> addImages(@PathVariable Long productId,
                                          @RequestBody @Valid List<ProductImageCreateRequest> dtos) {
        imageService.addImages(productId, dtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<Void> updateImage(@PathVariable Long productId,
                                            @PathVariable Long imageId,
                                            @RequestBody @Valid ProductImageUpdateRequest dto) {
        imageService.updateImage(productId, imageId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long productId,
                                            @PathVariable Long imageId) {
        imageService.deleteImage(productId, imageId);
        return ResponseEntity.noContent().build();
    }
}
