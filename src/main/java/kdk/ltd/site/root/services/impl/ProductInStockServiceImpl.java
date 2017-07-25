package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.*;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.repositories.ProductInStockRepository;
import kdk.ltd.site.root.repositories.ProductRepository;
import kdk.ltd.site.root.repositories.StorageRepository;
import kdk.ltd.site.root.services.ProductInStockService;
import kdk.ltd.site.root.services.exceptions.NegativeBalanceException;
import org.hibernate.engine.spi.EntityEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductInStockServiceImpl implements ProductInStockService {


    private ProductInStockRepository productInStockRepository;

    private StorageRepository storageRepository;

    private ProductRepository productRepository;

    private DealRepository dealRepository;

    private LocalDateTime currnetRemainingsDate;

    @PersistenceContext
    private EntityManager em;

    public ProductInStockServiceImpl() {
    }

    @Inject
    public ProductInStockServiceImpl(ProductInStockRepository productInStockRepository,
                                     StorageRepository storageRepository,
                                     ProductRepository productRepository,
                                     DealRepository dealRepository,
                                     LocalDateTime currentRemainingsDate) {
        this.productInStockRepository = productInStockRepository;
        this.storageRepository = storageRepository;
        this.productRepository = productRepository;
        this.dealRepository = dealRepository;
        this.currnetRemainingsDate = currentRemainingsDate;
    }

    @Override
    public ProductInStock findOne(InStockId id) {
        return productInStockRepository.findOne(id);
    }


    public ProductInStock findBy(Long productId, Long storageId) {
        //    Product product = productRepository.getOne(productId);
        //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByIdProductStorageWrapperProductIdAndIdProductStorageWrapperStorageId(productId, storageId).get();
    }

    @Override
    public List<ProductInStock> findByStorageId(Long storageId) {
        //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByIdProductStorageWrapperStorageId(storageId);
    }

    @Override
    public List<ProductInStock> findByProductId(Long productId) {
        //   Product product = productRepository.getOne(productId);
        return productInStockRepository.findByIdProductStorageWrapperProductId(productId);
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateProductsInStock(List<DealDetail> details) {
        details.forEach(this::update);
    }

    public void update(DealDetail d) {
        InStockId id = new InStockId(
                d.getProduct(),
                    d.getStorage(),
                        currnetRemainingsDate
        );

        ProductInStock inStock =
                productInStockRepository.findOne(id);

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
                        String.format("Product: %d Storage: %d",
                                id.getProductStorageWrapper().getProduct().getId(),
                                id.getProductStorageWrapper().getStorage().getId()));
            inStock.setQuantity(qnt);
            inStock.setSum(inStock.getSum().add(d.getSum()));
        }
    }

    @Override
    public void createPeriod() {
        createPeriod(LocalDateTime.now());
    }

    @Override
    public void createPeriod(final LocalDateTime target) {

        final LocalDateTime prev = productInStockRepository.findMaxDate();
        if (prev.compareTo(target) > 0)
            throw new IllegalArgumentException("Specified date is before previous period date");
        List<ProductInStock> newPeriod =
                productInStockRepository.findAllForNewPeriod(prev, target);

        if (!currnetRemainingsDate.equals(prev)) {
            addPreviousPeriod(newPeriod, prev, target);
        }

        newPeriod.forEach(p -> p.setRestDateTime(target));
        productInStockRepository.save(newPeriod);
    }

    private void addPreviousPeriod(List<ProductInStock> newPeriod, LocalDateTime last, LocalDateTime end) {
        Set<ProductStorageWrapper> wrappers =
                newPeriod.stream()
                .map(ProductInStock::getProductStorageWrapper)
                .collect(Collectors.toSet());

        List<ProductInStock> lastMatched =
                productInStockRepository
                        .findByIdProductStorageWrapperInAndIdRestDateTime(wrappers, last);
        List<ProductInStock> lastNotMatched =
                productInStockRepository
                        .findByIdProductStorageWrapperNotInAndIdRestDateTime(wrappers, last);

        newPeriod.forEach(p -> lastMatched.forEach(
                m -> changeProdInStock(p, m)
        ));

        lastNotMatched.forEach(p -> p.setRestDateTime(end));

        newPeriod.addAll(lastNotMatched);
    }

    private void changeProdInStock(ProductInStock p, ProductInStock m) {
        if (p.getProductStorageWrapper().equals(m.getProductStorageWrapper())) {
            p.setQuantity(p.getQuantity() + m.getQuantity());
            p.setSum(p.getSum().add(m.getSum()));
        }
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
