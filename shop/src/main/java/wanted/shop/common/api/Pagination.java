package wanted.shop.common.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class Pagination {

    @JsonProperty("total_items")
    private long totalItems;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("per_page")
    private int perPage;

    public Pagination(long totalItems, int totalPages, int currentPage, int perPage) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.perPage = perPage;
    }

    public static Pagination from(Page<?> page) {
        return new Pagination(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber() + 1,
                page.getSize()
        );
    }

}
