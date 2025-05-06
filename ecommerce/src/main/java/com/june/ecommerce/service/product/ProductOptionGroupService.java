package com.june.ecommerce.service.product;

import com.june.ecommerce.domain.productoption.ProductOption;
import com.june.ecommerce.domain.productoptiongroup.ProductOptionGroup;
import com.june.ecommerce.dto.option.ProductOptionDto;
import com.june.ecommerce.dto.option.ProductOptionGroupDto;
import com.june.ecommerce.repository.product.ProductOptionGroupRepository;
import com.june.ecommerce.repository.product.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOptionGroupService {

    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;


    public List<ProductOptionGroupDto> getOptionGroupsWithOptionsByProductId(int productId) {
        List<ProductOptionGroup> groups = productOptionGroupRepository.findByProductId(productId);

        return groups.stream()
                .map(group -> {
                    List<ProductOption> options = productOptionRepository.findByOptionGroupId(group.getId());

                    return ProductOptionGroupDto.fromEntity(group, options);
                })
                .collect(Collectors.toList());

    }
}
