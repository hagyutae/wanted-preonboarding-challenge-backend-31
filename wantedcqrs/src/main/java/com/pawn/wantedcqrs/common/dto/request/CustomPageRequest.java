package com.pawn.wantedcqrs.common.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CustomPageRequest {

    @Positive(message = "page must be positive")
    private int page = 1;

    @Positive(message = "perPage must be positive")
    private int perPage = 10;

    private String sort = "created_at:desc";

    public PageRequest convertToPageRequest() {
        if (this.page > 1) {
            this.page--;
        }

//		return PageRequest.of(page, perPage, Sort.by(sort));
        return PageRequest.of(page, perPage);
    }

}

