package investLee.platform.ecommerce.controller;

import investLee.platform.ecommerce.dto.request.*;
import investLee.platform.ecommerce.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;

    // 상품 등록
    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductCreateRequest request) {
        Long id = manageService.createProduct(request);
        return ResponseEntity.ok(id);
    }

    // 상품 수정
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest request
    ) {
        manageService.updateProduct(productId, request);
        return ResponseEntity.ok().build();
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        manageService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    // 옵션 추가
    @PostMapping("/{productId}/options")
    public ResponseEntity<Void> addOption(
            @PathVariable Long productId,
            @RequestBody OptionCreateRequest request
    ) {
        manageService.addOption(productId, request);
        return ResponseEntity.ok().build();
    }

    // 옵션 수정
    @PutMapping("/options/{optionId}")
    public ResponseEntity<Void> updateOption(
            @PathVariable Long optionId,
            @RequestBody OptionUpdateRequest request
    ) {
        manageService.updateOption(optionId, request);
        return ResponseEntity.ok().build();
    }

    // 옵션 삭제
    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        manageService.deleteOption(optionId);
        return ResponseEntity.ok().build();
    }

    // 이미지 추가
    @PostMapping("/{productId}/images")
    public ResponseEntity<Void> addImage(
            @PathVariable Long productId,
            @RequestBody ImageCreateRequest request
    ) {
        manageService.addImage(productId, request);
        return ResponseEntity.ok().build();
    }

    // 이미지 수정
    @PutMapping("/images/{imageId}")
    public ResponseEntity<Void> updateImage(
            @PathVariable Long imageId,
            @RequestBody ImageUpdateRequest request
    ) {
        manageService.updateImage(imageId, request);
        return ResponseEntity.ok().build();
    }

    // 이미지 삭제
    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        manageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}