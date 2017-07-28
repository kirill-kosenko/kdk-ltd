package kdk.ltd.site.root.services;

import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.RemainingProducts;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


public interface RemainingProductsService {
    RemainingProducts findOne(Long id);
    List<RemainingProducts> findByStorageId(Long storageId);
    List<RemainingProducts> findByProductId(Long productId);
    void update(Collection<DealDetail> details);
    void createNewDateTimePoint(LocalDateTime date);
    void createNewDateTimePoint();
    void deleteAll();
}
