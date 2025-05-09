package wanted.shop.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import wanted.shop.review.domain.entity.ReviewId;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    public UserId getId() {
        return new UserId(this.id);
    }

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
