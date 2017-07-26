package kdk.ltd.site.root.services;

import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.Detail;
import kdk.ltd.site.root.entities.ProductInStock;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


public interface ProductInStockService {
    ProductInStock findOne(Long id);
    List<ProductInStock> findByStorageId(Long storageId);
    List<ProductInStock> findByProductId(Long productId);
    void updateProductsInStock(Collection<DealDetail> details);
    void createNewDateTimePoint(LocalDateTime date);
    void createNewDateTimePoint();
    void deleteAll();
}
