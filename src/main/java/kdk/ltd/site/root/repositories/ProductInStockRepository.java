package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public interface ProductInStockRepository extends JpaRepository<ProductInStock, Long> {

    Optional<ProductInStock> findByProductIdAndStorageId(Long productId, Long storageId);
    @Query("select r from ProductInStock r where r.product = ?1 and r.storage = ?2 and r.dateTimePoint = ?3")
    Optional<ProductInStock> findByProductAndStorageAndDateTimePoint(Product product, Storage storage, LocalDateTime date);

    List<ProductInStock> findByStorageId(Long storageId);
    List<ProductInStock> findByProductId(Long productId);

    @Query(
            "select new kdk.ltd.site.root.entities.ProductInStock(dd.product, dd.storage, sum(dd.sum), sum(dd.quantity)) " +
                    "from Deal d join DealDetail dd on d.id = dd.deal.id and d.dateTimeOfDeal between ?1 and ?2" +
                    " group by dd.product, dd.storage")
    List<ProductInStock> createForNewPoint(LocalDateTime prevPoint, LocalDateTime newPoint);

    @Query("select sum(d.quantity) from DealDetail d where d.product.id = ?1 and d.storage.id = ?2")
    Long selectSum(Long productId, Long storageId);

    List<ProductInStock> findByDateTimePoint(LocalDateTime dateTime);

    @Query("select max(r.dateTimePoint) from ProductInStock r")
    LocalDateTime findPrevDateTimePoint();

}
