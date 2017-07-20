package kdk.ltd.site.root.repositories;

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


public interface ProductInStockRepository extends JpaRepository<ProductInStock, Long> {

    Optional<ProductInStock> findByProductIdAndStorageId(Long productId, Long storageId);
    @Query("select new ProductInStock(r.id, r.restDate, r.storage, r.product, r.quantity, r.sum) from ProductInStock r where r.product = ?1 and r.storage = ?2 and r.restDate = ?3")
    Optional<ProductInStock> findBy(Product product, Storage storage, LocalDate date);
    ProductInStock findByProductAndStorageAndRestDate(Product product, Storage storage, LocalDate date);

    List<ProductInStock> findByStorageId(Long storageId);
    List<ProductInStock> findByProductId(Long productId);
    @Query("select sum(r.quantity) from ProductInStock r where r.product.id = ?1")
    Integer findReamainingQuantityForProduct(Long productId);

    @Query("select new ProductInStock(dd.storage, dd.product, sum(dd.quantity), sum(dd.sum) ) " +
            "from DealDetail dd where dd.document.id in (?1)" +
            "group by dd.product, dd.storage")
    List<ProductInStock> prepareNewPeriod(List<Long> docsIds);
    @Query("select sum(d.quantity) from DealDetail d where d.product.id = ?1 and d.storage.id = ?2")
    Long selectSum(Long productId, Long storageId);
    @Query("select max(r.restDate) from ProductInStock r")
    LocalDate findMaxDate();
}
