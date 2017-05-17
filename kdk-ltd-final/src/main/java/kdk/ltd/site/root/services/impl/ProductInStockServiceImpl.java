package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.Product;
import kdk.ltd.site.root.entities.ProductInStock;
import kdk.ltd.site.root.entities.Storage;
import kdk.ltd.site.root.repositories.ProductRepository;
import org.hibernate.engine.spi.EntityEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import kdk.ltd.site.root.repositories.FactDealRepository;
import kdk.ltd.site.root.repositories.ProductInStockRepository;
import kdk.ltd.site.root.repositories.StorageRepository;
import kdk.ltd.site.root.services.ProductInStockService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Transactional
@Service
public class ProductInStockServiceImpl implements ProductInStockService {

    @Inject
    private ProductInStockRepository productInStockRepository;
    @Inject
    private StorageRepository storageRepository;
    @Inject
    private ProductRepository productRepository;
    @Inject
    private FactDealRepository factDealRepository;
    @PersistenceContext
    private EntityManager em;


    @Override
    public ProductInStock findOne(Long id) {
        return productInStockRepository.findOne(id);
    }

    @Override
    public ProductInStock findBy(Long productId, Long storageId) {
    //    Product product = productRepository.getOne(productId);
    //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByProductIdAndStorageId(productId, storageId).get();
    }

    @Override
    public List<ProductInStock> findByStorage(Long storageId) {
    //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByStorageId(storageId);
    }

    @Override
    public List<ProductInStock> findByProduct(Long productId) {
     //   Product product = productRepository.getOne(productId);
        return productInStockRepository.findByProductId(productId);
    }





    @Override
    @Transactional
    public void updateProductsInStock(List<DealDetail> details) {
        for (DealDetail d: details) {
            update(d);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updateRemainingAmount(DealDetail d) {
        Product product = d.getProduct();
        Storage storage = d.getStorage();
        System.err.println("Before obtaining rem");
        printAllManagedEntitiesInPersistenceContext();

        Optional<ProductInStock> remaining =
                productInStockRepository
                        .findBy(
                                product ,
                                storage,
                                ProductInStock.CURRENT_REMAININGS_DATE
                        );
        if (!remaining.isPresent()) {
            if (d.getQuantity() < 0)
                throw new RuntimeException(
                        String.format("RemainingAmount record for productId: %s and storageId: %s is not present in db",
                                product.getId(), storage.getId())
                );
            productInStockRepository.save(
                    new ProductInStock(ProductInStock.CURRENT_REMAININGS_DATE, storage, product, d.getQuantity(), d.getSum())
            );

        } else {
            ProductInStock rem = remaining.get();
            rem.addQnt(d.getQuantity()).addSumm(d.getSum());

        }
        System.err.println("After obtaining rem");
        printAllManagedEntitiesInPersistenceContext();
    }

    public void update(DealDetail d) {
        Product product = d.getProduct();
        Storage storage = d.getStorage();
     //   System.err.println("Before obtaining rem");
     //   printAllManagedEntitiesInPersistenceContext();

        Long id = productInStockRepository.findId(
                                product ,
                                storage,
                                ProductInStock.CURRENT_REMAININGS_DATE
                        );
        if (id == null) {
            if (d.getQuantity() < 0)
                throw new RuntimeException(
                        String.format("RemainingAmount record for productId: %s and storageId: %s is not present in db",
                                product.getId(), storage.getId())
                );
            productInStockRepository.save(
                    new ProductInStock(ProductInStock.CURRENT_REMAININGS_DATE, storage, product, d.getQuantity(), d.getSum())
            );

        } else {
            productInStockRepository.addQuantityAndSum(d.getQuantity(), d.getSum(), id);

        }
     //   System.err.println("After obtaining rem");
     //   printAllManagedEntitiesInPersistenceContext();
    }

    @Override
    public void createPeriod(LocalDate end) {

        LocalDate lastDate = productInStockRepository.findMaxDate();
        if (lastDate.compareTo(end) > 0)
            throw new IllegalArgumentException("Date is incorrect");

        List<Long> docsIds = factDealRepository.findIdsBetween(lastDate, end.minusDays(1));

        if (docsIds.size() == 0 )
            throw new RuntimeException("There are no amounts to save");

        List<ProductInStock> rems = productInStockRepository.prepareNewPeriod(docsIds);
        rems.forEach(r -> r.setRestDate(end));
        productInStockRepository.save(rems);
    }

    private void printAllManagedEntitiesInPersistenceContext() {
        final org.hibernate.engine.spi.SessionImplementor session = em.unwrap( org.hibernate.engine.spi.SessionImplementor.class );
        final org.hibernate.engine.spi.PersistenceContext pc = session.getPersistenceContext();
        final Map.Entry<Object,org.hibernate.engine.spi.EntityEntry>[] entityEntries = pc.reentrantSafeEntityEntries();
        for (Map.Entry<Object, EntityEntry> entry: entityEntries) {
            System.err.println("Key: " + entry.getKey() + "Value: " + entry.getValue());
        }
    }

    @Override
    public void deleteAll() {
        this.productInStockRepository.deleteAll();
    }

}
