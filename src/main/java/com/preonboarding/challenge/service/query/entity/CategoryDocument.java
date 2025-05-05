package com.preonboarding.challenge.service.query.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDocument {

    @Id
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    private String imageUrl;

    private ParentCategory parent;

    @Builder.Default
    private List<ChildCategory> children = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParentCategory {
        private Long id;
        private String name;
        private String slug;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChildCategory {
        private Long id;
        private String name;
        private String slug;
        private Integer level;
    }
}