package com.example.preonboarding.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private Long categoryId;
    private boolean isPrimary;
}
