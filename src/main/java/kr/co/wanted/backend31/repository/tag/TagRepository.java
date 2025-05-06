package kr.co.wanted.backend31.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.wanted.backend31.common.model.tag.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
