package wanted.domain.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.brand.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
