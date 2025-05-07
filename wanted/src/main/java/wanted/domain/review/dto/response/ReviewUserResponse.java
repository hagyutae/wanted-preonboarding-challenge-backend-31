package wanted.domain.review.dto.response;

import wanted.domain.user.entity.User;

public record ReviewUserResponse(
        Long id,
        String name,
        String avatarUrl
) {
    public static ReviewUserResponse of(User user){
        return new ReviewUserResponse(
                user.getId(),
                user.getName(),
                user.getAvatarUrl()
        );
    }
}
