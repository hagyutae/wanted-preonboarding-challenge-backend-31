package com.preonboarding.challenge.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.CategoryDocument;
import com.preonboarding.challenge.query.document.CategoryDocumentRepository;
import com.preonboarding.challenge.query.sync.CdcEvent;
import com.preonboarding.challenge.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class CategoryDMEHandler extends ProductDocumentModelEventHandler {

    private final CategoryDocumentRepository categoryDocumentRepository;
    private final CategoryRepository categoryRepository;

    public CategoryDMEHandler(
            ObjectMapper objectMapper,
            CategoryDocumentRepository categoryDocumentRepository,
            CategoryRepository categoryRepository) {
        super(objectMapper);
        this.categoryDocumentRepository = categoryDocumentRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "categories";
    }

    @Override
    public void handle(CdcEvent event) {
        Map<String, Object> data;
        Long categoryId;

        if (event.isDelete()) {
            // 삭제 이벤트 처리
            data = event.getBeforeData();
            if (data == null || !data.containsKey("id")) {
                return;
            }

            categoryId = getLongValue(data, "id");
            categoryDocumentRepository.deleteById(categoryId);

            // 하위 카테고리가 있을 수 있으므로 부모 카테고리 정보도 업데이트
            updateParentCategoriesChildren(categoryId);

            log.info("Deleted category document with ID: {}", categoryId);
            return;
        }

        // 생성 또는 업데이트 이벤트 처리
        data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        categoryId = getLongValue(data, "id");

        // MongoDB에서 기존 문서 조회 또는 새 문서 생성
        CategoryDocument document = categoryDocumentRepository.findById(categoryId)
                .orElse(CategoryDocument.builder()
                        .id(categoryId)
                        .children(new ArrayList<>())
                        .build());

        // 기본 정보 업데이트
        document.setName(getStringValue(data, "name"));
        document.setSlug(getStringValue(data, "slug"));
        document.setDescription(getStringValue(data, "description"));
        document.setLevel(getIntegerValue(data, "level"));
        document.setImageUrl(getStringValue(data, "image_url"));

        // 부모 카테고리 정보 설정
        Long parentId = getLongValue(data, "parent_id");
        if (parentId != null) {
            // 부모 카테고리 정보 조회 및 설정
            Optional<CategoryDocument> parentCategory = categoryDocumentRepository.findById(parentId);

            if (parentCategory.isPresent()) {
                CategoryDocument.ParentCategory parent = CategoryDocument.ParentCategory.builder()
                        .id(parentId)
                        .name(parentCategory.get().getName())
                        .slug(parentCategory.get().getSlug())
                        .build();
                document.setParent(parent);

                // 부모 카테고리의 children 목록에 현재 카테고리 추가
                updateParentCategoryChildren(parentId, categoryId, document.getName(), document.getSlug(), document.getLevel());
            } else {
                // 부모 카테고리가 아직 MongoDB에 동기화되지 않은 경우
                // JPA 저장소에서 기본 정보만 가져와서 설정
                log.warn("Parent category {} not found in MongoDB, fetching from JPA repository", parentId);
                categoryRepository.findById(parentId).ifPresent(jpaCategory -> {
                    CategoryDocument.ParentCategory parent = CategoryDocument.ParentCategory.builder()
                            .id(parentId)
                            .name(jpaCategory.getName())
                            .slug(jpaCategory.getSlug())
                            .build();
                    document.setParent(parent);
                });
            }
        } else {
            document.setParent(null);
        }

        // 문서 저장
        categoryDocumentRepository.save(document);
        log.info("Saved category document with ID: {}", categoryId);
    }

    private void updateParentCategoriesChildren(Long removedChildId) {
        // 부모 카테고리들의 children 목록에서 삭제된 카테고리 제거
        List<CategoryDocument> allCategories = categoryDocumentRepository.findAll();
        for (CategoryDocument category : allCategories) {
            boolean updated = category.getChildren().removeIf(child -> child.getId().equals(removedChildId));
            if (updated) {
                categoryDocumentRepository.save(category);
                log.info("Removed child category {} from parent {}", removedChildId, category.getId());
            }
        }
    }

    private void updateParentCategoryChildren(Long parentId, Long childId, String childName, String childSlug, Integer childLevel) {
        // 부모 카테고리 조회
        Optional<CategoryDocument> parentOpt = categoryDocumentRepository.findById(parentId);
        if (parentOpt.isEmpty()) {
            return;
        }

        CategoryDocument parent = parentOpt.get();
        List<CategoryDocument.ChildCategory> children = parent.getChildren();

        // 기존 자식 카테고리 인덱스 찾기
        int existingIndex = -1;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getId().equals(childId)) {
                existingIndex = i;
                break;
            }
        }

        // 자식 카테고리 업데이트 또는 추가
        CategoryDocument.ChildCategory childInfo = CategoryDocument.ChildCategory.builder()
                .id(childId)
                .name(childName)
                .slug(childSlug)
                .level(childLevel)
                .build();

        if (existingIndex >= 0) {
            children.set(existingIndex, childInfo);
        } else {
            children.add(childInfo);
        }

        // 부모 카테고리 저장
        categoryDocumentRepository.save(parent);
    }
}