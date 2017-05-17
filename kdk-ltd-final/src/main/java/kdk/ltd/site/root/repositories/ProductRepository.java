package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByParentIsNotNull();
    List<Product> findAllByParentIsNull();
    List<Product> findAllByParentIsNotNullAndNameLike(String name);
}
