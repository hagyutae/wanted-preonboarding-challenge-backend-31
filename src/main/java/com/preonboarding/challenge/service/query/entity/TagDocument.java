package com.preonboarding.challenge.service.query.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDocument {

    @Id
    private Long id;
    private String name;
    private String slug;
}