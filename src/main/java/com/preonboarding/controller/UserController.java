package com.preonboarding.controller;

import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    @PostMapping("/{id}/tokens")
    public ResponseEntity<BaseResponse> getTestToken(@PathVariable("id") Long userId) {
        String testToken = JwtUtil.createJwt(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.builder()
                        .success(true)
                        .message("test token 생성 성공")
                        .data(testToken)
                        .build());
    }
}
