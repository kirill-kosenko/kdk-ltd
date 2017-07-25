package kdk.ltd.site.root.services;

import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.InStockId;
import kdk.ltd.site.root.entities.ProductInStock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ProductInStockService {
    ProductInStock findOne(InStockId id);
    List<ProductInStock> findByStorageId(Long storageId);
    List<ProductInStock> findByProductId(Long productId);
    void updateProductsInStock(List<DealDetail> details);
    void createPeriod(LocalDateTime date);
    void createPeriod();
    void deleteAll();
}
