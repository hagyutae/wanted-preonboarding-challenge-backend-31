package investLee.platform.ecommerce.controller;

import investLee.platform.ecommerce.dto.request.ProductOptionGroupCreateRequest;
import investLee.platform.ecommerce.dto.request.ProductOptionUpdateRequest;
import investLee.platform.ecommerce.service.ProductOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{productId}/options")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService optionService;

    @PostMapping
    public ResponseEntity<Void> addOptionGroup(@PathVariable Long productId,
                                               @RequestBody @Valid ProductOptionGroupCreateRequest dto) {
        optionService.addOptionGroup(productId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{optionId}")
    public ResponseEntity<Void> updateOption(@PathVariable Long productId,
                                             @PathVariable Long optionId,
                                             @RequestBody @Valid ProductOptionUpdateRequest dto) {
        optionService.updateOption(productId, optionId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long productId,
                                             @PathVariable Long optionId) {
        optionService.deleteOption(productId, optionId);
        return ResponseEntity.noContent().build();
    }
}