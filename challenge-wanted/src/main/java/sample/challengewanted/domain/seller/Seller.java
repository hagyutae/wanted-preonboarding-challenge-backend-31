package sample.challengewanted.domain.seller;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sellers")
@Entity
public class Seller {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String logoUrl;
    private Double rating;

    private String contactEmail;
    private String contactPhone;

    @OneToMany(mappedBy = "seller")
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        this.products.add(product);
        product.assignSeller(this);
    }
}
