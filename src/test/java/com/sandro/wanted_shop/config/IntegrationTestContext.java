package com.sandro.wanted_shop.config;

import com.sandro.wanted_shop.category.CategoryRepository;
import com.sandro.wanted_shop.product.persistence.OptionGroupRepository;
import com.sandro.wanted_shop.product.persistence.OptionRepository;
import com.sandro.wanted_shop.product.persistence.ProductRepository;
import com.sandro.wanted_shop.review.ReviewService;
import com.sandro.wanted_shop.review.entity.ReviewRepository;
import com.sandro.wanted_shop.review.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Sql(
        scripts = {
                "classpath:sql/clean_up.sql",
                "classpath:sql/users.sql",
                "classpath:sql/brands.sql",
                "classpath:sql/sellers.sql",
                "classpath:sql/products.sql",
                "classpath:sql/categories.sql",
                "classpath:sql/tags.sql",
                "classpath:sql/product_extended.sql",
                "classpath:sql/product_options.sql",
                "classpath:sql/reviews.sql",
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@Sql(
        scripts = "classpath:sql/clean_up.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTestContext {
    @Autowired protected MockMvc mvc;
    @Autowired protected ReviewService reviewService;
    @Autowired protected ProductRepository productRepository;
    @Autowired protected UserRepository userRepository;
    @Autowired protected ReviewRepository reviewRepository;
    @Autowired protected OptionRepository optionRepository;
    @Autowired protected OptionGroupRepository optionGroupRepository;
}
