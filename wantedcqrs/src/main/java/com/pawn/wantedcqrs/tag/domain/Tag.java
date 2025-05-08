package com.pawn.wantedcqrs.tag.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    name: 태그명
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    //    slug: URL 슬러그
    @Column(name = "slug", length = 100, nullable = false, unique = true)
    private String slug;

    @Builder
    protected Tag(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

}
