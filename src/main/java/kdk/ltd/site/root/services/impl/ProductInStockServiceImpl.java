package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.InStockId;
import kdk.ltd.site.root.entities.ProductInStock;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.repositories.ProductInStockRepository;
import kdk.ltd.site.root.repositories.ProductRepository;
import kdk.ltd.site.root.repositories.StorageRepository;
import kdk.ltd.site.root.services.ProductInStockService;
import kdk.ltd.site.root.services.exceptions.NegativeBalanceException;
import org.hibernate.engine.spi.EntityEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


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
    private DealRepository dealRepository;
    @PersistenceContext
    private EntityManager em;


    @Override
    public ProductInStock findOne(InStockId id) {
        return productInStockRepository.findOne(id);
    }


    public ProductInStock findBy(Long productId, Long storageId) {
        //    Product product = productRepository.getOne(productId);
        //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByIdProductIdAndIdStorageId(productId, storageId).get();
    }

    @Override
    public List<ProductInStock> findByStorageId(Long storageId) {
        //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByIdStorageId(storageId);
    }

    @Override
    public List<ProductInStock> findByProductId(Long productId) {
        //   Product product = productRepository.getOne(productId);
        return productInStockRepository.findByIdProductId(productId);
    }


    @Override
    @Transactional
    public void updateProductsInStock(List<DealDetail> details) {
        for (DealDetail d : details) {
            update(d);
        }
    }

    public void update(DealDetail d) {
        InStockId id = new InStockId(
                d.getProduct(),
                d.getStorage(),
                ProductInStock.CURRENT_REMAININGS_DATE
        );
        //   System.err.println("Before obtaining rem");
        //   printAllManagedEntitiesInPersistenceContext();

        ProductInStock inStock = productInStockRepository.
                                            findOne(id);
        if (inStock == null) {
            if (d.getQuantity() < 0)
                throw new NegativeBalanceException(
                        String.format("Record with id = %s does not exist", id)
                );
            productInStockRepository.save(
                    new ProductInStock(
                            id, d.getQuantity(), d.getSum())
            );

        } else {
            int qnt = inStock.getQuantity() + d.getQuantity();
            if (qnt < 0)
                throw new NegativeBalanceException(
                        String.format("Product: %d Storage: %d", id.getProduct().getId(), id.getStorage().getId()));
            inStock.setQuantity(qnt);
            inStock.setSum(inStock.getSum().add(d.getSum()));
        }
        //   System.err.println("After obtaining rem");
        //   printAllManagedEntitiesInPersistenceContext();
    }

    @Override
    public void createPeriod(LocalDate end) {

        LocalDate lastDate = productInStockRepository.findMaxDate();
        if (lastDate.compareTo(end) > 0)
            throw new IllegalArgumentException("Date is incorrect");

        List<Long> docsIds = dealRepository.findIdsBetween(lastDate, end.minusDays(1));

        if (docsIds.size() == 0)
            throw new RuntimeException("There are no amounts to save");

        List<ProductInStock> rems = productInStockRepository.prepareNewPeriod(docsIds);
        rems.forEach(r -> r.getId().setRestDate(end));
        productInStockRepository.save(rems);
    }

    private void printAllManagedEntitiesInPersistenceContext() {
        final org.hibernate.engine.spi.SessionImplementor session = em.unwrap(org.hibernate.engine.spi.SessionImplementor.class);
        final org.hibernate.engine.spi.PersistenceContext pc = session.getPersistenceContext();
        final Map.Entry<Object, org.hibernate.engine.spi.EntityEntry>[] entityEntries = pc.reentrantSafeEntityEntries();
        for (Map.Entry<Object, EntityEntry> entry : entityEntries) {
            System.err.println("Key: " + entry.getKey() + "Value: " + entry.getValue());
        }
    }

    @Override
    public void deleteAll() {
        this.productInStockRepository.deleteAll();
    }

}
