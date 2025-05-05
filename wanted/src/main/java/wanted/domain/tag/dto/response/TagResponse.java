package wanted.domain.tag.dto.response;

import wanted.domain.tag.entity.Tag;

public record TagResponse(
        Long id,
        String name,
        String slug
) {
    public static TagResponse of(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getSlug()
        );
    }
}