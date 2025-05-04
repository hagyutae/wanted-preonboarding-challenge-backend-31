package com.ecommerce.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.util.Strings;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductListRequest(
    Integer page,
    Integer perPage,
    String sort,
    String status,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    List<Long> category,
    Long seller,
    Long brand,
    Boolean inStock,
    List<Long> tag,
    String search,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate createdFrom,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate createdTo
){
    public ProductListRequest {
        if (page == null || page < 1) {
            page = 1;
        }
        if (perPage == null || perPage < 10) {
            perPage = 10;
        }
        if (Strings.isBlank(sort)) {
            sort = "created_at:desc";
        }
    }
}