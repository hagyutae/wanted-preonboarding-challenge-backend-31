package wanted.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
