package wanted.shop.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.brand.respository.BrandRepository;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.dto.ProductCreateRequest;
import wanted.shop.product.dto.ProductCreateResponse;
import wanted.shop.product.respository.ProductRepository;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.seller.respository.SellerRepository;
import wanted.shop.tag.domain.entity.Tag;
import wanted.shop.tag.domain.entity.TagId;
import wanted.shop.tag.respository.TagRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductReferenceService productReferenceService;
    private final ProductRepository productRepository;

    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request) {

        SellerId sellerId = request.getSellerId();
        Seller seller = productReferenceService.getSeller(sellerId);

        BrandId brandId = request.getBrandId();
        Brand brand = productReferenceService.getBrand(brandId);

        List<TagId> tagIds = request.getTagIds();
        List<Tag> tags = productReferenceService.getTags(tagIds);

        Product product = request.toProduct(seller, brand, tags, );

        productRepository.save(product);
        return null;
    }




}
