package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name= "name")
    private String name;

    @Column(name= "slug")
    private String slug;

    @Column(name= "short_description")
    private String short_description;

    @Column(name= "full_description")
    private String full_description;

    @Column(name= "created_at")
    private LocalDateTime created_at;

    @Column(name= "updated_at")
    private LocalDateTime updated_at;

    @Column(name= "seller_id")
    private Integer seller_id;

    @Column(name= "brand_id")
    private Integer brand_id;

    @Column(name= "status")
    private String status;
}