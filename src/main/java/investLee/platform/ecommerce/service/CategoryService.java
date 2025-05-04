package investLee.platform.ecommerce.service;

import investLee.platform.ecommerce.domain.entity.CategoryEntity;
import investLee.platform.ecommerce.dto.response.CategoryTreeResponse;
import investLee.platform.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryTreeResponse> getCategoryTree() {
        List<CategoryEntity> allCategories = categoryRepository.findAll();

        // ID 기준 맵핑
        Map<Long, CategoryTreeResponse> map = new HashMap<>();

        // 1. 모든 카테고리를 DTO로 변환
        for (CategoryEntity entity : allCategories) {
            map.put(entity.getId(), CategoryTreeResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .slug(entity.getSlug())
                    .description(entity.getDescription())
                    .level(entity.getLevel())
                    .imageUrl(entity.getImageUrl())
                    .children(new ArrayList<>())
                    .build());
        }

        // 2. 부모 - 자식 연결
        List<CategoryTreeResponse> rootList = new ArrayList<>();
        for (CategoryEntity entity : allCategories) {
            CategoryTreeResponse dto = map.get(entity.getId());

            if (entity.getParent() != null) {
                CategoryTreeResponse parentDto = map.get(entity.getParent().getId());
                parentDto.getChildren().add(dto);
            } else {
                rootList.add(dto);
            }
        }

        return rootList;
    }
}