package minseok.cqrschallenge.product.repository;

import minseok.cqrschallenge.product.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository  extends CrudRepository<Product, Long> {


}
