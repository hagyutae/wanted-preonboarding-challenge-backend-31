package com.wanted_preonboarding_challenge_backend.eCommerce.domain;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option_group")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private Integer displayOrder;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    public void addProduct(Product product){
        this.product = product;
    }
    public void addOption(ProductOption option){
        options.add(option);

        if(option.getOptionGroup() != this){
            option.addOptionGroup(this);
        }
    }
}