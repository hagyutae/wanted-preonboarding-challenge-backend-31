package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.query.entity.ProductSearchDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductSearchOperationsImpl implements ProductSearchOperations {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<ProductSearchDocument> searchProductsByConditions(ProductQuery.ListProducts command) {
        Pageable pageable = command.getPagination().toPageable();

        // criteria 생성
        List<Criteria> criteriaList = new ArrayList<>();

        // 상태 필터
        if (command.getStatus() != null) {
            criteriaList.add(new Criteria("status").is(command.getStatus().toUpperCase()));
        }

        // 가격 범위 필터
        if (command.getMinPrice() != null) {
            criteriaList.add(new Criteria("basePrice").greaterThanEqual(command.getMinPrice()));
        }

        if (command.getMaxPrice() != null) {
            criteriaList.add(new Criteria("basePrice").lessThanEqual(command.getMaxPrice()));
        }

        // 카테고리 필터
        if (command.getCategory() != null && !command.getCategory().isEmpty()) {
            criteriaList.add(new Criteria("categoryIds").in(command.getCategory()));
        }

        // 판매자 필터
        if (command.getSeller() != null) {
            criteriaList.add(new Criteria("sellerId").is(command.getSeller()));
        }

        // 브랜드 필터
        if (command.getBrand() != null) {
            criteriaList.add(new Criteria("brandId").is(command.getBrand()));
        }

        // 태그 필터
        if (command.getTag() != null && !command.getTag().isEmpty()) {
            criteriaList.add(new Criteria("tagIds").in(command.getTag()));
        }

        // 재고 여부 필터
        if (command.getInStock() != null) {
            criteriaList.add(new Criteria("inStock").is(command.getInStock()));
        }

        // 검색어 필터 (OR 조건으로 연결)
        if (command.getSearch() != null && !command.getSearch().isEmpty()) {
            Criteria searchCriteria = new Criteria().or("name").contains(command.getSearch())
                    .or("shortDescription").contains(command.getSearch())
                    .or("fullDescription").contains(command.getSearch())
                    .or("materials").contains(command.getSearch());
            criteriaList.add(searchCriteria);
        }

        // 등록일 범위 필터
        if (command.getCreatedFrom() != null) {
            long fromTimestamp = command.getCreatedFrom().atStartOfDay()
                    .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            criteriaList.add(new Criteria("createdAt").greaterThanEqual(fromTimestamp));
        }

        if (command.getCreatedTo() != null) {
            LocalDateTime endOfDay = command.getCreatedTo().plusDays(1).atStartOfDay().minusSeconds(1);
            long toTimestamp = endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            criteriaList.add(new Criteria("createdAt").lessThanEqual(toTimestamp));
        }

        // 정렬 설정 적용
        String[] sortParams = command.getPagination().getSort().split(":");
        String sortField = sortParams[0]; // 이미 camelCase로 변환되어 있다고 가정
        String sortDirection = sortParams.length > 1 ? sortParams[1] : "desc";

        // 정렬 필드 매핑 (필요한 경우 필드명 변환)
        String esSortField = mapSortField(sortField);
        Sort sort = "asc".equalsIgnoreCase(sortDirection) ?
                Sort.by(Sort.Direction.ASC, esSortField) :
                Sort.by(Sort.Direction.DESC, esSortField);

        // 모든 criteria를 조합하여 쿼리 생성
        CriteriaQuery query;
        if (!criteriaList.isEmpty()) {
            Criteria criteria = criteriaList.get(0);
            for (int i = 1; i < criteriaList.size(); i++) {
                criteria = criteria.and(criteriaList.get(i));
            }
            query = new CriteriaQuery(criteria, pageable).addSort(sort);
        } else {
            query = new CriteriaQuery(new Criteria(), pageable).addSort(sort);
        }

        log.debug("Elasticsearch query: {}", query);

        return elasticsearchOperations.search(query, ProductSearchDocument.class, IndexCoordinates.of("products"));
    }

    // 정렬 필드 매핑 (필요한 경우 필드명 변환)
    private String mapSortField(String sortField) {
        switch (sortField) {
            case "createdAt":
                return "createdAt";
            case "price":
                return "salePrice";
            case "rating":
                return "averageRating";
            case "name":
                return "name";
            default:
                return "createdAt";
        }
    }
}