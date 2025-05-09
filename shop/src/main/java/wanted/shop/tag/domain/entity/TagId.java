package wanted.shop.tag.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class TagId implements Serializable {

    private final Long value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagId tagId)) return false;
        return Objects.equals(value, tagId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
