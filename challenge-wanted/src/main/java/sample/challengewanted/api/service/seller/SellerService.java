package sample.challengewanted.api.service.seller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.seller.Seller;
import sample.challengewanted.domain.seller.SellerRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    public Seller findById(ProductCreateRequest request) {
        return sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));
    }
}
