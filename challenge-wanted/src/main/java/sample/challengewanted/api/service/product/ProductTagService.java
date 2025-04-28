package sample.challengewanted.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.product.entity.Product;
import sample.challengewanted.domain.tag.ProductTag;
import sample.challengewanted.domain.tag.ProductTagRepository;
import sample.challengewanted.domain.tag.Tag;
import sample.challengewanted.domain.tag.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductTagService {

    private final ProductTagRepository productTagRepository;
    private final TagRepository tagRepository;

    public void saveProductTags(ProductCreateRequest request, Product product) {
        List<ProductTag> tags = request.getTags().stream()
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(each -> {
                    Tag tag = each.get();
                    return ProductTag.create(tag, product);
                })
                .toList();

        productTagRepository.saveAll(tags);
    }

}
