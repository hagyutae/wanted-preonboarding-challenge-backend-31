package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.query.entity.ProductSearchDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
            Criteria searchCriteria = new Criteria().or("name").matches(command.getSearch())
                    .or("shortDescription").matches(command.getSearch())
                    .or("fullDescription").matches(command.getSearch())
                    .or("materials").matches(command.getSearch());
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

        // 모든 criteria를 조합하여 쿼리 생성
        CriteriaQuery query;
        if (!criteriaList.isEmpty()) {
            Criteria criteria = criteriaList.get(0);
            for (int i = 1; i < criteriaList.size(); i++) {
                criteria = criteria.and(criteriaList.get(i));
            }
            query = new CriteriaQuery(criteria, pageable);
        } else {
            query = new CriteriaQuery(new Criteria(), pageable);
        }

        log.debug("Elasticsearch query: {}", query);

        return elasticsearchOperations.search(query, ProductSearchDocument.class, IndexCoordinates.of("products"));
    }
}