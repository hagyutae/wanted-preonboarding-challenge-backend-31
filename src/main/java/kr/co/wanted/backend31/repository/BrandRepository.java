package kr.co.wanted.backend31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.wanted.backend31.common.model.brand.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
