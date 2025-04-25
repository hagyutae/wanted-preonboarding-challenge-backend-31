package com.mkhwang.wantedcqrs.auth.dto;

import lombok.Getter;

@Getter
public class AccessToken {
  private String accessToken;

  private AccessToken() {
  }

  private AccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public static AccessToken of(String accessToken) {
    return new AccessToken(accessToken);
  }
}
