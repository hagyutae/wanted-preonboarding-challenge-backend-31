package sample.challengewanted.api.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.product.request.OptionGroupRequest;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.product.entity.Product;
import sample.challengewanted.domain.product.entity.ProductOption;
import sample.challengewanted.domain.product.entity.ProductOptionGroup;
import sample.challengewanted.domain.product.repository.ProductOptionGroupRepository;
import sample.challengewanted.domain.product.repository.ProductOptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductOptionGroupService {

    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;

    public List<ProductOption> saveGroupWithOptions(ProductCreateRequest request, Product product) {
        List<OptionGroupRequest> groups = request.getOptionGroups();

        List<ProductOption> result = new ArrayList<>();

        for (OptionGroupRequest group : groups) {
            ProductOptionGroup savedGroup = productOptionGroupRepository.save(
                    ProductOptionGroup.create(group.getName(), group.getDisplayOrder(), product)
            );

            List<ProductOption> options = group.getOptions().stream()
                    .map(each -> productOptionRepository.save(
                            ProductOption.of(
                                    each.getName(),
                                    each.getSku(),
                                    each.getStock(),
                                    each.getDisplayOrder(),
                                    savedGroup
                            )
                    ))
                    .toList();

            result.addAll(options);
        }

        return result;
    }
}
