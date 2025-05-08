package com.pawn.wantedcqrs.productOptionGroup.service;

import com.pawn.wantedcqrs.productOptionGroup.dto.ProductOptionDto;
import com.pawn.wantedcqrs.productOptionGroup.dto.ProductOptionGroupDto;
import com.pawn.wantedcqrs.productOptionGroup.entity.ProductOptionGroup;
import com.pawn.wantedcqrs.productOptionGroup.repository.ProductOptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductOptionGroupService {

    private final ProductOptionGroupRepository productOptionGroupRepository;

    @Transactional
    public List<ProductOptionGroupDto> createProductOptionGroupsByProductId(Long productId, List<ProductOptionGroupDto> productOptionGroupParams) {

        List<ProductOptionGroup> productOptionGroups = productOptionGroupParams.stream().map(dto -> ProductOptionGroup.builder()
                .productId(productId)
                .name(dto.getName())
                .displayOrder(dto.getDisplayOrder())
                .options(dto.getOptions().stream()
                        .map(ProductOptionDto::toEntity)
                        .collect(Collectors.toList())
                ).build()
        ).toList();
        List<ProductOptionGroup> savedProductOptionGroups = productOptionGroupRepository.saveAll(productOptionGroups);

        return savedProductOptionGroups.stream()
                .map(ProductOptionGroupDto::fromEntity)
                .toList();
    }

}
