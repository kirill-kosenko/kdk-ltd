package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface RemainingProductsRepository extends JpaRepository<RemainingProducts, Long> {

    Optional<RemainingProducts> findByProductIdAndStorageId(Long productId, Long storageId);
    @Query("select r from RemainingProducts r where r.product = ?1 and r.storage = ?2 and r.dateTimePoint = ?3")
    Optional<RemainingProducts> findByProductAndStorageAndDateTimePoint(Product product, Storage storage, LocalDateTime date);

    List<RemainingProducts> findByStorageId(Long storageId);
    List<RemainingProducts> findByProductId(Long productId);

    @Query(
            "select new kdk.ltd.site.root.entities.RemainingProducts(dd.product, dd.storage, sum(dd.sum), sum(dd.quantity)) " +
                    "from Deal d join DealDetail dd on d.id = dd.deal.id and d.dateTimeOfDeal between ?1 and ?2" +
                    " group by dd.product, dd.storage")
    List<RemainingProducts> createForNewPoint(LocalDateTime prevPoint, LocalDateTime newPoint);

    @Query("select sum(d.quantity) from DealDetail d where d.product.id = ?1 and d.storage.id = ?2")
    Long selectSum(Long productId, Long storageId);

    List<RemainingProducts> findByDateTimePoint(LocalDateTime dateTime);

    @Query("select max(r.dateTimePoint) from RemainingProducts r")
    LocalDateTime findPrevDateTimePoint();

}
