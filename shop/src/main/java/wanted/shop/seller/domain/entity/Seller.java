package wanted.shop.seller.domain.entity;

import jakarta.persistence.*;
import wanted.shop.product.domain.entity.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_id_seq")
    @SequenceGenerator(name = "seller_id_seq", sequenceName = "seller_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Lob
    private String description;

    private String logoUrl;

    private Double rating;

    private String contactEmail;
    private String contactPhone;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}