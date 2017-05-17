package kdk.ltd.site.root.services;

import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.ProductInStock;

import java.time.LocalDate;
import java.util.List;


public interface ProductInStockService {
    ProductInStock findOne(Long id);
    ProductInStock findBy(Long productId, Long storageId);
    List<ProductInStock> findByStorage(Long storageId);
    List<ProductInStock> findByProduct(Long productId);
    void updateProductsInStock(List<DealDetail> details);
    void createPeriod(LocalDate date);
    void deleteAll();
}
