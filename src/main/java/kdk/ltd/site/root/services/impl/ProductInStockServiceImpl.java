package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.*;
import kdk.ltd.site.root.repositories.ProductInStockRepository;
import kdk.ltd.site.root.services.ProductInStockService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductInStockServiceImpl implements ProductInStockService {

    private final LocalDateTime CURRENT_DATETIME_POINT;

    private ProductInStockRepository productInStockRepository;

    @PersistenceContext
    private EntityManager em;

    @Inject
    public ProductInStockServiceImpl(ProductInStockRepository productInStockRepository,
                                     LocalDateTime currentDateTimePoint) {
        this.productInStockRepository = productInStockRepository;
        this.CURRENT_DATETIME_POINT = currentDateTimePoint;
    }

    @Override
    public ProductInStock findOne(Long id) {
        return productInStockRepository.findOne(id);
    }


    public ProductInStock findBy(Long productId, Long storageId) {
        //    Product product = productRepository.getOne(productId);
        //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByProductIdAndStorageId(productId, storageId).get();
    }

    @Override
    public List<ProductInStock> findByStorageId(Long storageId) {
        //    Storage storage = storageRepository.getOne(storageId);
        return productInStockRepository.findByStorageId(storageId);
    }

    @Override
    public List<ProductInStock> findByProductId(Long productId) {
        //   Product product = productRepository.getOne(productId);
        return productInStockRepository.findByProductId(productId);
    }


    @Override
    public void updateProductsInStock(Collection<DealDetail> details) {
        details.forEach(this::saveOrUpdateRemaining);
    }

    public void saveOrUpdateRemaining(DealDetail d) {

        Optional<ProductInStock> inStock =
                    productInStockRepository.findByProductAndStorageAndDateTimePoint(
                            d.getProduct(), d.getStorage(), CURRENT_DATETIME_POINT
                    );

        if (inStock.isPresent())
            addQntAndSumTo( inStock.get(), d.getQuantity(), d.getSum() );
        else
            createAndSaveRemaining( d );
    }

    private void createAndSaveRemaining(DealDetail d) {
        productInStockRepository.save(
                new ProductInStock(
                       d.getProduct(), d.getStorage(), d.getQuantity(), d.getSum(), CURRENT_DATETIME_POINT )
        );
        em.flush();
    }

    private void addQntAndSumTo(ProductInStock r, Integer qnt, BigDecimal sum) {
        r.setSum( r.getSum().add(sum) );
        r.setQuantity( r.getQuantity() + qnt);
    }

    @Override
    public void createNewDateTimePoint() {
        createNewDateTimePoint( LocalDateTime.now() );
    }

    @Override
    public void createNewDateTimePoint(final LocalDateTime newPoint) {

        final LocalDateTime prevPoint =
                productInStockRepository.findPrevDateTimePoint();

        if ( prevPoint.compareTo( newPoint ) > 0 )
            throw new IllegalArgumentException("Specified DateTimePoint is before previous");

        List<ProductInStock> newRemainings =
                productInStockRepository.createForNewPoint( prevPoint, newPoint );

        if ( !CURRENT_DATETIME_POINT.equals( prevPoint ) ) {
            addRemainingsFromPrevPoint( newRemainings, prevPoint );
        }

        newRemainings.forEach( p -> p.setDateTimePoint( newPoint ));
        productInStockRepository.save( newRemainings );
    }

    private void addRemainingsFromPrevPoint( List<ProductInStock> newRemainings,
                                             LocalDateTime prevPoint ) {

        List<ProductInStock> prevRemainings =
                productInStockRepository.findByDateTimePoint(prevPoint);

        merge(newRemainings, prevRemainings);
    }

    private void merge( List<ProductInStock> newRems,
                        List<ProductInStock> prevRems ) {

        Map<Long, ProductInStock> matched =
                changeQntAndSumFromPrevRems( newRems, prevRems );

        addPreviousRemainingsNotExistedIn(newRems, prevRems, matched);
    }

    /**
     * Changes quantity and sum of every matched new Remaining with previous Remaining.
     * @param newRems - list of new point Remainings
     * @param prevRems - list of previous point Remainings
     * @return Map of all previous Remainings that have been used for changing new Remainings
     */
    private Map<Long, ProductInStock> changeQntAndSumFromPrevRems(List<ProductInStock> newRems,
                                             final List<ProductInStock> prevRems) {
        final Map<Long, ProductInStock> matched = new HashMap<>();
        newRems.forEach(newRem ->
                prevRems.forEach(p -> {
                    if (newRem.eqaulsByProductAndStorage(p)) {
                        addQntAndSumTo(newRem, p.getQuantity(), p.getSum());
                        matched.put(p.getId(), p);
                    }
                })
        );
        return matched;
    }

    /**
     * Add all previous Remainings that new Remainings doesn't have
     * @param newRems - list of new point Remainings
     * @param prevRems - list of previous point Remainings
     * @param matched - Map of all previous Remainings that have been used
     *                  for changing new Remainings
     */
    private void addPreviousRemainingsNotExistedIn(List<ProductInStock> newRems,
                                                   List<ProductInStock> prevRems,
                                                   final Map<Long, ProductInStock> matched) {
        newRems.addAll(
                prevRems.stream()
                        .filter(r -> matched.get(r.getId()) == null)
                        .map(ProductInStock::new)
                        .collect(Collectors.toList()));
    }

    @Override
    public void deleteAll() {
        this.productInStockRepository.deleteAll();
    }

}
