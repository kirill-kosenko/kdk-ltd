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


public interface ProductInStockRepository extends JpaRepository<ProductInStock, InStockId> {

    Optional<ProductInStock> findByIdProductStorageWrapperProductIdAndIdProductStorageWrapperStorageId(Long productId, Long storageId);
  /*  @Query("select r from ProductInStock r where r.id.product = ?1 and r.id.storage = ?2 and r.id.restDateTime = ?3")
    Optional<ProductInStock> findBy(Product product, Storage storage, LocalDate date);*/

    List<ProductInStock> findByIdProductStorageWrapperStorageId(Long storageId);
    List<ProductInStock> findByIdProductStorageWrapperProductId(Long productId);
    @Query("select sum(r.quantity) from ProductInStock r where r.id.productStorageWrapper.product.id = ?1")
    Integer findReamainingQuantityForProduct(Long productId);

  /*  @Query("select new ProductInStock(dd.storage, dd.product, sum(dd.quantity), sum(dd.sum) ) " +
            "from DealDetail dd where dd.deal.id in (?1)" +
            "group by dd.product, dd.storage")
    List<ProductInStock> prepareNewPeriod(List<Long> docsIds);
    */

    @Query(
            "select new kdk.ltd.site.root.entities.ProductInStock(dd.product, dd.storage, sum(dd.quantity), sum(dd.sum)) " +
                    "from Deal d join DealDetail dd on d.id = dd.deal.id and d.dateTimeOfDeal between :prev and :target" +
                    " group by dd.product, dd.storage")
    List<ProductInStock> findAllForNewPeriod(@Param("prev") LocalDateTime prev, @Param("target") LocalDateTime target);

    @Query("select sum(d.quantity) from DealDetail d where d.product.id = ?1 and d.storage.id = ?2")
    Long selectSum(Long productId, Long storageId);

    List<ProductInStock> findByIdRestDateTime(LocalDateTime dateTime);

    @Query("select max(r.id.restDateTime) from ProductInStock r")
    LocalDateTime findMaxDate();

    @Query(
            "select new kdk.ltd.site.root.entities.ProductInStock(p.id, p.quantity, p.sum) from ProductInStock p " +
                    "where p.id.productStorageWrapper in (?1)"
    )
    List<ProductInStock> findByInProductSorageWrapper(Set<ProductStorageWrapper> set);

    @Query(
            "select new kdk.ltd.site.root.entities.ProductInStock(p.id, p.quantity, p.sum) from ProductInStock p " +
                    "where p.id.productStorageWrapper not in (?1)"
    )
    List<ProductInStock> findByNotInProductStorageWrapper(Set<ProductStorageWrapper> set);

    List<ProductInStock> findByIdProductStorageWrapperInAndIdRestDateTime(Set<ProductStorageWrapper> wrappers, LocalDateTime dateTime);

    List<ProductInStock> findByIdProductStorageWrapperNotInAndIdRestDateTime(Set<ProductStorageWrapper> ids, LocalDateTime restDate);
}
