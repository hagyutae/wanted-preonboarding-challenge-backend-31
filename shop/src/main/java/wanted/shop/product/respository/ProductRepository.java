package wanted.shop.product.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.entity.ProductId;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductRepository {

    private ProductDataRepository dataRepository;

    public Optional<Product> findById(ProductId productId) {
        return dataRepository.findById(productId.getValue());
    }

    public Product save(Product product) {
        return dataRepository.save(product);
    }
}
