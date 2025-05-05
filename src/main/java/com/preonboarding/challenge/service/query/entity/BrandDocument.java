package com.preonboarding.challenge.service.query.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brands")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDocument {

    @Id
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String website;
}