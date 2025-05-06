package com.example.demo.repository;

import com.example.demo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ProductsRepository extends JpaRepository<Products,Integer>
{

    @Query(value =
            "SELECT p.id, p.name, p.slug, p.short_description, p.full_description, " +
                    "p.created_at, p.updated_at, p.seller_id, p.brand_id, p.status, " +
                    "s.id as seller_id, s.name as seller_name, s.description as seller_description, " +
                    "s.logo_url as seller_logo_url, s.rating as seller_ratings, " +
                    "s.contact_email as seller_contact_email, s.created_at as seller_created_at, " +
                    "b.id as brand_id, b.name as brand_name, b.slug as brand_slug, " +
                    "b.description as brand_description, b.logo_url as brand_logo_url, " +
                    "b.website as brand_website, " +
                    "pd.id as product_detail_id, pd.product_id as p_detail_product_id, pd.weight, pd.dimensions, " +
                    "pd.materials, pd.country_of_origin, " +
                    "pd.warranty_info, pd.care_instructions, " +
                    "pd.additional_info, " +
                    "pp.id as product_price_id, pp.product_id as product_price_product_id, pp.base_price, pp.cost_price, pp.currency, pp.tax_rate, " +
                    "pog.id as option_group_id, pog.product_id as option_group_product_id, " +
                    "pog.name as option_group_name, pog.display_order as option_group_display_order, " +
                    "po.id as option_id, po.option_group_id as option_option_group_id, " +
                    "po.name as option_name, po.additional_price, " +
                    "po.sku, po.stock, po.display_order as option_display_order, " +
                    "pi.id as image_id, pi.product_id as image_product_id, " +
                    "pi.url as image_url, pi.alt_text, " +
                    "pi.is_primary, pi.display_order as image_display_order, " +
                    "pi.option_id, " +
                    "pc.id as p_category_id, pc.product_id as p_category_product_id, " +
                    "pc.category_id as p_category_category_id, pc.is_primary as category_is_primary, " +
                    "c.id as category_id, c.name as category_name, " +
                    "c.slug as category_slug, c.description as category_description, " +
                    "c.parent_id, c.level, c.image_url as category_image_url, " +
                    "pt.id as p_tag_id, pt.product_id as p_tag_product_id, " +
                    "pt.tag_id as p_tag_tag_id, " +
                    "t.id as tag_id, t.name as tag_name, " +
                    "t.slug as tag_slug " +
                    "FROM products p " +
                    "LEFT OUTER JOIN sellers s ON p.seller_id = s.id " +
                    "LEFT OUTER JOIN brands b ON p.brand_id = b.id " +
                    "LEFT OUTER JOIN product_details pd ON p.id = pd.product_id " +
                    "LEFT OUTER JOIN product_prices pp ON p.id = pp.product_id " +
                    "LEFT OUTER JOIN product_option_groups pog ON p.id = pog.product_id " +
                    "LEFT OUTER JOIN product_options po ON pog.id = po.option_group_id " +
                    "LEFT OUTER JOIN product_images pi ON p.id = pi.product_id " +
                    "LEFT OUTER JOIN product_categories pc ON p.id = pc.product_id " +
                    "LEFT OUTER JOIN categories c ON pc.category_id = c.id " +
                    "LEFT OUTER JOIN product_tags pt ON p.id = pt.product_id " +
                    "LEFT OUTER JOIN tags t ON pt.tag_id = t.id",
            nativeQuery = true)
    List<Map<String, Object>> findAllProductDetails();

    @Query(value =
            "SELECT p.id, p.name, p.slug, p.short_description, p.full_description, " +
                    "p.created_at, p.updated_at, p.seller_id, p.brand_id, p.status, " +
                    "s.id as seller_id, s.name as seller_name, s.description as seller_description, " +
                    "s.logo_url as seller_logo_url, s.rating as seller_ratings, " +
                    "s.contact_email as seller_contact_email, s.created_at as seller_created_at, " +
                    "b.id as brand_id, b.name as brand_name, b.slug as brand_slug, " +
                    "b.description as brand_description, b.logo_url as brand_logo_url, " +
                    "b.website as brand_website, " +
                    "pd.id as product_detail_id, pd.product_id as p_detail_product_id, pd.weight, pd.dimensions, " +
                    "pd.materials, pd.country_of_origin, " +
                    "pd.warranty_info, pd.care_instructions, " +
                    "pd.additional_info, " +
                    "pp.id as product_price_id, pp.product_id as product_price_product_id, pp.base_price, pp.cost_price, pp.currency, pp.tax_rate, " +
                    "pog.id as option_group_id, pog.product_id as option_group_product_id, " +
                    "pog.name as option_group_name, pog.display_order as option_group_display_order, " +
                    "po.id as option_id, po.option_group_id as option_option_group_id, " +
                    "po.name as option_name, po.additional_price, " +
                    "po.sku, po.stock, po.display_order as option_display_order, " +
                    "pi.id as image_id, pi.product_id as image_product_id, " +
                    "pi.url as image_url, pi.alt_text, " +
                    "pi.is_primary, pi.display_order as image_display_order, " +
                    "pi.option_id, " +
                    "pc.id as p_category_id, pc.product_id as p_category_product_id, " +
                    "pc.category_id as p_category_category_id, pc.is_primary as category_is_primary, " +
                    "c.id as category_id, c.name as category_name, " +
                    "c.slug as category_slug, c.description as category_description, " +
                    "c.parent_id, c.level, c.image_url as category_image_url, " +
                    "pt.id as p_tag_id, pt.product_id as p_tag_product_id, " +
                    "pt.tag_id as p_tag_tag_id, " +
                    "t.id as tag_id, t.name as tag_name, " +
                    "t.slug as tag_slug " +
                    "FROM products p " +
                    "LEFT OUTER JOIN sellers s ON p.seller_id = s.id " +
                    "LEFT OUTER JOIN brands b ON p.brand_id = b.id " +
                    "LEFT OUTER JOIN product_details pd ON p.id = pd.product_id " +
                    "LEFT OUTER JOIN product_prices pp ON p.id = pp.product_id " +
                    "LEFT OUTER JOIN product_option_groups pog ON p.id = pog.product_id " +
                    "LEFT OUTER JOIN product_options po ON pog.id = po.option_group_id " +
                    "LEFT OUTER JOIN product_images pi ON p.id = pi.product_id " +
                    "LEFT OUTER JOIN product_categories pc ON p.id = pc.product_id " +
                    "LEFT OUTER JOIN categories c ON pc.category_id = c.id " +
                    "LEFT OUTER JOIN product_tags pt ON p.id = pt.product_id " +
                    "LEFT OUTER JOIN tags t ON pt.tag_id = t.id " +
                    "WHERE p.id = :productId",
            nativeQuery = true)
    List<Map<String, Object>> findProductDetailsById(@Param("productId") Integer productId);
}
