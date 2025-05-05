package com.preonboarding.challenge.service.query.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(indexName = "products")
@Setting(settingPath = "elasticsearch/product-index-settings.json")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDocument {

    @Id
    private Long id;

    // 한글 검색을 위한 nori 분석기 적용
    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private String name;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private String shortDescription;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private String fullDescription;

    // materials 필드 추가 (product_details 테이블에서 가져옴)
    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private String materials;

    @Field(type = FieldType.Keyword)
    private String status; // ACTIVE, OUT_OF_STOCK, DELETED

    @Field(type = FieldType.Double)
    private BigDecimal basePrice;

    @Field(type = FieldType.Double)
    private BigDecimal salePrice;

    @Field(type = FieldType.Keyword)
    @Builder.Default
    private List<Long> categoryIds = new ArrayList<>();

    @Field(type = FieldType.Keyword)
    private Long sellerId;

    @Field(type = FieldType.Keyword)
    private Long brandId;

    @Field(type = FieldType.Keyword)
    @Builder.Default
    private List<Long> tagIds = new ArrayList<>();

    @Field(type = FieldType.Boolean)
    private Boolean inStock;

    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Instant createdAt;

    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Instant updatedAt;

    // 리뷰 관련 정보
    @Field(type = FieldType.Double)
    private Double averageRating;

    @Field(type = FieldType.Integer)
    private Integer reviewCount;

    // 필터링과 정렬을 위한 추가 필드
    @Field(type = FieldType.Keyword)
    private String slug;
}