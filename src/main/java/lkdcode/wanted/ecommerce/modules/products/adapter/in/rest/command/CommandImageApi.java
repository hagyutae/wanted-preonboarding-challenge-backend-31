package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command;


import jakarta.validation.Valid;
import lkdcode.wanted.ecommerce.framework.common.api.response.ApiResponse;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.image.AddImageDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.image.AddImageService;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommandImageApi {
    private final AddImageService service;

    @PostMapping("/api/products/{id}/images")
    public ResponseEntity<?> getAddImage(
        @PathVariable(name = "id") final Long id,
        @Valid @RequestBody final AddImageDTO request
    ) {
        final var model = request.getAddImageModel(new ProductId(id));
        final var response = service.addImage(model);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse.success(
                    response,
                    "상품 이미지가 성공적으로 추가되었습니다."
                )
            );
    }
}
