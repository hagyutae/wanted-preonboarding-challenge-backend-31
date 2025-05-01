package com.june.ecommerce.service.product;

import com.june.ecommerce.domain.brand.Brand;
import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.seller.Seller;
import com.june.ecommerce.dto.product.ProductRequestDto;
import com.june.ecommerce.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;

    public Product createProduct(ProductRequestDto dto) {

        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));


        Product product = Product.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(seller)
                .brand(brand)
                .status(dto.getStatus())
                .build();

        return productRepository.save(product);
    }
}
