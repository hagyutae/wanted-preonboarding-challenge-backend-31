package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.user;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.user.User;

public record UserInfoDto(
        Long id,
        String name,
        String avatarUrl
) {

    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getName(),
                user.getAvatarUrl()
        );
    }
}
