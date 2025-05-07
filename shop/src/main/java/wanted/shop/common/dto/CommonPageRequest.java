package wanted.shop.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Setter
public class CommonPageRequest {
    protected int page = 0;
    protected int perPage = 10;
    protected String sort = "created_at:desc";

    public Pageable toPageable() {
        String[] sortPart = sort.split(":");
        String sortKey = sortPart[0];

        Sort.Direction direction = Sort.Direction.DESC;
        if (sortPart[1].equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        return PageRequest.of(page, perPage, Sort.by(direction, sortKey));
    }
}
