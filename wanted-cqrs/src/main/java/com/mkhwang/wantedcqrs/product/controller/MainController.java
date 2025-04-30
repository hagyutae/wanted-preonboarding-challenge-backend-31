package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.config.advice.ApiMessage;
import com.mkhwang.wantedcqrs.product.application.MainSearchService;
import com.mkhwang.wantedcqrs.product.domain.dto.main.MainResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Main", description = "Main API")
@RestController
@RequiredArgsConstructor
public class MainController {
  private final MainSearchService mainSearchService;

  @ApiMessage("main.success")
  @Operation(summary = "Main API", description = "Main API")
  @GetMapping("/api/main")
  public MainResponseDto getMainProduct() {
    return mainSearchService.getMainProduct();
  }
}
