package com.example.wanted_preonboarding_challenge_backend_31.application.tag;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CommonErrorType;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CustomException;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.tag.Tag;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.tag.TagRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagQueryService {

    private final TagRepository tagRepository;

    public List<Tag> getAllByIds(List<Long> ids) {
        List<Tag> tags = tagRepository.findAllById(ids);
        validateSize(ids, tags);
        return tags;
    }

    private void validateSize(List<Long> ids, List<Tag> tags) {
        if (ids.size() != tags.size()) {
            Set<Long> foundIds = tags.stream().map(Tag::getId).collect(Collectors.toSet());
            List<Long> missingIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new CustomException(
                    ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND,
                            "요청한 태그를 찾을 수 없습니다, 찾지 못한 ID:" + missingIds));
        }
    }
}
