package com.ecommerce.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReviewCreateRequest(
    Integer rating,
    String title,
    String content
) {}