package wanted.shop.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
public class User {

    @EmbeddedId
    private UserId userid;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name")),
            @AttributeOverride(name = "email", column = @Column(name = "email")),
            @AttributeOverride(name = "avatar_url", column = @Column(name = "avatar_url"))
    })
    private UserInfo userInfo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
