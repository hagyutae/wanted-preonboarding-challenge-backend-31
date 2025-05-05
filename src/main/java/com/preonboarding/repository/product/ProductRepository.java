package com.preonboarding.repository.product;

import com.preonboarding.domain.Product;
import com.preonboarding.dto.request.product.ProductSearchRequestDto;
import com.preonboarding.repository.product.querydsl.ProductSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>, ProductSearchRepository {
}
