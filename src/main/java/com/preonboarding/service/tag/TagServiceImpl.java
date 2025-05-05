package com.preonboarding.service.tag;

import com.preonboarding.domain.ProductTag;
import com.preonboarding.domain.Tag;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductTag> createProductTag(List<Long> tagIds) {
        List<ProductTag> productTagList = new ArrayList<>();

        for (Long tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.TAG_NOT_FOUND)));

            ProductTag productTag = ProductTag.builder()
                    .tag(tag)
                    .build();
            productTagList.add(productTag);
        }

        return productTagList;
    }
}
