package minseok.cqrschallenge.tag.repository;

import minseok.cqrschallenge.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
