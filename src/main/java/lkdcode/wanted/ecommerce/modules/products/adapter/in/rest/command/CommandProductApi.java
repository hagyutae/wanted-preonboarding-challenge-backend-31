package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command;

import jakarta.validation.Valid;
import lkdcode.wanted.ecommerce.framework.common.api.response.ApiResponse;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create.UpsertProductDTO;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.response.UpsertResponse;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.mapper.CommandProductApiMapper;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.create.CreateProductService;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.delete.DeleteProductService;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.update.UpdateProductService;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommandProductApi {
    private final CreateProductService createService;
    private final UpdateProductService updateService;
    private final DeleteProductService deleteService;
    private final CommandProductApiMapper mapper;

    @PostMapping("/api/products")
    public ResponseEntity<ApiResponse<UpsertResponse>> getCreateProduct(
        @Valid @RequestBody final UpsertProductDTO request
    ) {
        final var response = createService.create(mapper.convertCreateModel(request));

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse.success(
                    mapper.convert(response),
                    "상품이 성공적으로 등록되었습니다."
                )
            );
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<UpsertResponse>> getUpdateProduct(
        @PathVariable(name = "id") final Long id,
        @Valid @RequestBody final UpsertProductDTO request
    ) {
        final var response = updateService.update(new ProductId(id), mapper.convertUpdateModel(request));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    mapper.convert(response),
                    "상품이 성공적으로 수정되었습니다."
                )
            );
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<Void>> getDeleteProduct(
        @PathVariable(name = "id") final Long id
    ) {
        deleteService.delete(new ProductId(id));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    null,
                    "상품이 성공적으로 삭제되었습니다."
                )
            );
    }
}