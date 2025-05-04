package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("태그")
@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("태그명")
    @Column(nullable = false, unique = true)
    private String name;

    @Comment("URL 슬러그")
    @Column(nullable = false, unique = true)
    private String slug;

}