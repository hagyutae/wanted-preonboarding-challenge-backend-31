package com.example.preonboarding.details.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class Dimensions {
    private int width;
    private int height;
    private int depth;


    public Dimensions(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
}
