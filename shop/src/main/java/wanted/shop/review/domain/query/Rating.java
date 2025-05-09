package wanted.shop.review.domain.query;

import lombok.Getter;

@Getter
public class Rating {

    private int value;

    public Rating(int value) {
        this.value = value;
    }

}
