package wanted.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.common.response.SuccessResponse;
import wanted.domain.auth.dto.request.SignInRequest;
import wanted.domain.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping()
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(SuccessResponse.ok(authService.signIn(signInRequest)));
    }
}
