package wanted.shop.brand.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class BrandRepository {

    private BrandDataRepository dataRepository;

    public Optional<Brand> findById(BrandId brandId) {
        return dataRepository.findById(brandId.getValue());
    }

    public Brand save(Brand brand) {
        return dataRepository.save(brand);
    }
}
