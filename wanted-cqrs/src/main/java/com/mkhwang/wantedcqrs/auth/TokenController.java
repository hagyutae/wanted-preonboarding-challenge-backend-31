package com.mkhwang.wantedcqrs.auth;

import com.mkhwang.wantedcqrs.auth.dto.AccessToken;
import com.mkhwang.wantedcqrs.config.security.jwt.JwtProvider;
import com.mkhwang.wantedcqrs.user.domain.LoginUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token", description = "Token API")
@RestController
@RequiredArgsConstructor
public class TokenController {
  private final JwtProvider jwtProvider;

  @GetMapping("/api/issue/token")
  public AccessToken accessToken(@AuthenticationPrincipal LoginUser loginUser) {
    return AccessToken.of(jwtProvider.generateToken(loginUser.getId()));
  }
}
