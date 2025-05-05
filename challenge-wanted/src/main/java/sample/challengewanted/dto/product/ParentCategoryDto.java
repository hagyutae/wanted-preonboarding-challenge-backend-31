package sample.challengewanted.dto.product;

public record ParentCategoryDto(
        Long id,
        String name,
        String slug
) {
   public static ParentCategoryDto of(Long id, String name, String slug) {
       return new ParentCategoryDto(id, name, slug);
   }
}
