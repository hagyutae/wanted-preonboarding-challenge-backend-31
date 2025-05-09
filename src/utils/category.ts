import { Prisma, Category } from '@prisma/client';

export interface CategoryWithChildren extends Category {
  children?: CategoryWithChildren[];
}

export function collectCategoryIds(category: CategoryWithChildren): number[] {
  const ids = [category.id];
  if (category.children) {
    category.children.forEach((child) => {
      ids.push(...collectCategoryIds(child));
    });
  }
  return ids;
}

export function getCategoryIds(
  category: CategoryWithChildren,
  includeSubcategories: boolean
): number[] {
  return includeSubcategories ? collectCategoryIds(category) : [category.id];
}

export function isCategoryWithChildren(category: Category | null): category is CategoryWithChildren {
  return category !== null && 'children' in category;
} 