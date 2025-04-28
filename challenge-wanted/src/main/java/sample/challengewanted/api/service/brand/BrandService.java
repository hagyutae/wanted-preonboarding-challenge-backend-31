package sample.challengewanted.api.service.brand;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.brand.Brand;
import sample.challengewanted.domain.brand.BrandRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand findById(ProductCreateRequest request) {
        return brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
    }
}
