package com.example.demo.review.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ReviewDistribution(
        Long five,
        Long four,
        Long three,
        Long two,
        Long one
) {
    @QueryProjection
    public ReviewDistribution {
        // null 방지 처리
        five = five == null ? 0L : five;
        four = four == null ? 0L : four;
        three = three == null ? 0L : three;
        two = two == null ? 0L : two;
        one = one == null ? 0L : one;
    }

    public Long getTotal() {
        return five + four + three + two + one;
    }

    public Double getAverage() {
        if (this.getTotal() == 0L)
            return 0.0;
        double average = 1.0 * (five * 5 + four * 4 + three * 3 + two * 2 + one) / this.getTotal();
        return Math.round(average * 10.0) / 10.0;
    }
}


