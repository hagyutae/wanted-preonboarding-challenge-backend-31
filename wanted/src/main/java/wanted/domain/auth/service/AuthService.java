package wanted.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.common.security.TokenProvider;
import wanted.domain.auth.dto.request.SignInRequest;
import wanted.domain.auth.dto.response.TokenResponse;
import wanted.domain.user.entity.User;
import wanted.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public TokenResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));

        return getToken(user);
    }

    private TokenResponse getToken(User user) {
        Long userId = user.getId();
        String username = user.getEmail();

        String accessToken = "Bearer " + tokenProvider.getAccessToken(userId, username);

        return TokenResponse.of(accessToken);
    }
}
