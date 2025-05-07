package wanted.domain.auth.dto.request;

import jakarta.validation.constraints.Email;

public record SignInRequest(
        @Email
        String email
) {
}
