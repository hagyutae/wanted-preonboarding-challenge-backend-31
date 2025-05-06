package com.pawn.wantedcqrs.common.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomPageResponse<T> {

    private CustomPagination customPagination;

    private List<T> items;

    protected CustomPageResponse(CustomPagination customPagination, List<T> items) {
        this.customPagination = customPagination;
        this.items = items;
    }

    public static <T> CustomPageResponse<T> of(Page<T> page) {
        return new CustomPageResponse(
                new CustomPagination(page.getTotalElements(), page.getNumber(), page.getTotalPages())
                , page.getContent()
        );
    }

    private static class CustomPagination {

        private Long totalCount;

        private int pageIndex;

        private int pageSize;

        public CustomPagination(Long totalCount, int pageIndex, int pageSize) {
            this.totalCount = totalCount;
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
    }
}
