package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.InStockId;
import kdk.ltd.site.root.entities.Product;
import kdk.ltd.site.root.entities.ProductInStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import kdk.ltd.site.root.entities.Storage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ProductInStockRepository extends JpaRepository<ProductInStock, InStockId> {

    Optional<ProductInStock> findByIdProductIdAndIdStorageId(Long productId, Long storageId);
    @Query("select r from ProductInStock r where r.id.product = ?1 and r.id.storage = ?2 and r.id.restDate = ?3")
    Optional<ProductInStock> findBy(Product product, Storage storage, LocalDate date);

    List<ProductInStock> findByIdStorageId(Long storageId);
    List<ProductInStock> findByIdProductId(Long productId);
    @Query("select sum(r.quantity) from ProductInStock r where r.id.product.id = ?1")
    Integer findReamainingQuantityForProduct(Long productId);

    @Query("select new ProductInStock(dd.storage, dd.product, sum(dd.quantity), sum(dd.sum) ) " +
            "from DealDetail dd where dd.document.id in (?1)" +
            "group by dd.product, dd.storage")
    List<ProductInStock> prepareNewPeriod(List<Long> docsIds);
    @Query("select sum(d.quantity) from DealDetail d where d.product.id = ?1 and d.storage.id = ?2")
    Long selectSum(Long productId, Long storageId);
    @Query("select max(r.id.restDate) from ProductInStock r")
    LocalDate findMaxDate();
}
