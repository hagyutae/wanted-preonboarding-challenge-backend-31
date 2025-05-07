package wanted.shop.common.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Pagination {
    @JsonProperty("total_items")
    private int totalItems;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("per_page")
    private int perPage;

}
