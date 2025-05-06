package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command;

import jakarta.validation.Valid;
import lkdcode.wanted.ecommerce.framework.common.api.response.ApiResponse;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.option.AddOptionDTO;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.option.UpdateOptionDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.add.AddOptionService;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.delete.DeleteOptionService;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.update.UpdateOptionService;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommandOptionApi {
    private final AddOptionService addOptionService;
    private final UpdateOptionService updateOptionService;
    private final DeleteOptionService deleteOptionService;

    @PostMapping("/api/products/{id}/options")
    public ResponseEntity<ApiResponse<CommandOptionResult>> getAddOption(
        @PathVariable(name = "id") Long id,
        @Valid @RequestBody final AddOptionDTO request
    ) {
        final var response = addOptionService.addOption(new ProductId(id), request.getAddOptionModel());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse.success(
                    response,
                    "상품 옵션이 성공적으로 추가되었습니다."
                )
            );
    }

    @PutMapping("/api/products/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<CommandOptionResult>> getUpdateOption(
        @PathVariable(name = "id") final Long id,
        @PathVariable(name = "optionId") final Long optionId,
        @Valid @RequestBody final UpdateOptionDTO request
    ) {
        final var model = request.getAddOptionModel(new ProductOptionId(optionId));
        final var response = updateOptionService.update(new ProductId(id), model);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    response,
                    "상품 옵션이 성공적으로 수정되었습니다."
                )
            );
    }

    @DeleteMapping("/api/products/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<Void>> getDeleteOption(
        @PathVariable(name = "id") final Long id,
        @PathVariable(name = "optionId") final Long optionId
    ) {
        deleteOptionService.delete(new ProductId(id), new ProductOptionId(optionId));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    null,
                    "상품 옵션이 성공적으로 삭제되었습니다."
                )
            );
    }
}