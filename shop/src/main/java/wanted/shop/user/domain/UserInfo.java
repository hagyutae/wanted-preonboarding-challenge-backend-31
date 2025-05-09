package wanted.shop.user.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class UserInfo {
    private String name;
    private String email;
    private String avatarUrl;
}
