package com.sandro.wanted_shop.review.dto;

import com.sandro.wanted_shop.review.entity.User;

public record UserDto(
        String name,
        String email,
        String avatarUrl
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl()
        );
    }
}
