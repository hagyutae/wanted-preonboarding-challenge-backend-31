package lkdcode.wanted.ecommerce.modules.users.adapter.infrastructure.jpa.repository;

import lkdcode.wanted.ecommerce.modules.users.adapter.infrastructure.jpa.entity.User;
import org.springframework.data.repository.Repository;

public interface UseJpaRepository extends Repository<User, Long> {
}
