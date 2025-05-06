package com.shopping.mall.seller.entity;

import com.shopping.mall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seller")
@Getter
@NoArgsConstructor
public class Seller extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private Double rating;

    private String contactEmail;

    private String contactPhone;
}
