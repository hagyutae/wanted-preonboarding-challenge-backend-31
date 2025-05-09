package wanted.shop.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.brand.respository.BrandRepository;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.seller.respository.SellerRepository;
import wanted.shop.tag.domain.entity.Tag;
import wanted.shop.tag.domain.entity.TagId;
import wanted.shop.tag.respository.TagRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductReferenceService {

    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;

    public Seller getSeller(SellerId sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("없는 seller입니다"));
    }

    public Brand getBrand(BrandId brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("없는 brand입니다"));
    }

    public List<Tag> getTags(List<TagId> tagIds) {
        return tagIds.stream()
                .map(tagId -> {
                    return tagRepository.findById(tagId)
                            .orElseThrow(() -> new RuntimeException("없는 tag입니다"));
                }).toList();
    }
}
