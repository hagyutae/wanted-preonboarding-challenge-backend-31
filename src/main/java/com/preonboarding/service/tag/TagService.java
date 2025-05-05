package com.preonboarding.service.tag;

import com.preonboarding.domain.ProductTag;

import java.util.List;

public interface TagService {
    List<ProductTag> createProductTag(List<Long> tagIds);
}
