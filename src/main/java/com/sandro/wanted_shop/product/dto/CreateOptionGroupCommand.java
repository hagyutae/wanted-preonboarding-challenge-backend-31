package com.sandro.wanted_shop.product.dto;

import java.util.List;

public record CreateOptionGroupCommand(
        String name,
        Integer displayOrder,
        List<CreateOptionCommand> options
) {
}
