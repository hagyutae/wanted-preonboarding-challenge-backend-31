package com.mkhwang.wantedcqrs.user.controller;

import com.mkhwang.wantedcqrs.user.domain.LoginUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User API")
@RestController
public class MeController {

  @GetMapping("/api/users/me")
  public LoginUser getMe(@AuthenticationPrincipal LoginUser loginUser) {
    return loginUser;
  }
}
