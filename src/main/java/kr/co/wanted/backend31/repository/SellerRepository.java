package kr.co.wanted.backend31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.wanted.backend31.common.model.seller.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}
