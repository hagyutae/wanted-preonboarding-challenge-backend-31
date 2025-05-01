package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductTag;
import com.wanted.ecommerce.tag.domain.Tag;
import java.util.List;

public interface ProductTagService {

    List<ProductTag> saveAllProductTags(Product product, List<Tag> tags);
}
