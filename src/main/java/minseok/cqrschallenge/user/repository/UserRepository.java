package minseok.cqrschallenge.user.repository;

import minseok.cqrschallenge.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
