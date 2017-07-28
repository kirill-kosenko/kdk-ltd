package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.*;
import kdk.ltd.site.root.repositories.RemainingProductsRepository;
import kdk.ltd.site.root.services.RemainingProductsService;
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
public class RemainingProductsServiceImpl implements RemainingProductsService {

    private final LocalDateTime CURRENT_DATETIME_POINT;

    private RemainingProductsRepository remainingProductsRepository;

    @PersistenceContext
    private EntityManager em;

    @Inject
    public RemainingProductsServiceImpl(RemainingProductsRepository remainingProductsRepository,
                                        LocalDateTime currentDateTimePoint) {
        this.remainingProductsRepository = remainingProductsRepository;
        this.CURRENT_DATETIME_POINT = currentDateTimePoint;
    }

    @Override
    public RemainingProducts findOne(Long id) {
        return remainingProductsRepository.findOne(id);
    }


    public RemainingProducts findBy(Long productId, Long storageId) {
        //    Product product = productRepository.getOne(productId);
        //    Storage storage = storageRepository.getOne(storageId);
        return remainingProductsRepository.findByProductIdAndStorageId(productId, storageId).get();
    }

    @Override
    public List<RemainingProducts> findByStorageId(Long storageId) {
        //    Storage storage = storageRepository.getOne(storageId);
        return remainingProductsRepository.findByStorageId(storageId);
    }

    @Override
    public List<RemainingProducts> findByProductId(Long productId) {
        //   Product product = productRepository.getOne(productId);
        return remainingProductsRepository.findByProductId(productId);
    }


    @Override
    public void update(Collection<DealDetail> details) {
        details.forEach(this::saveOrUpdateRemaining);
    }

    public void saveOrUpdateRemaining(DealDetail d) {

        Optional<RemainingProducts> inStock =
                    remainingProductsRepository.findByProductAndStorageAndDateTimePoint(
                            d.getProduct(), d.getStorage(), CURRENT_DATETIME_POINT
                    );

        if (inStock.isPresent())
            addQntAndSumTo( inStock.get(), d.getQuantity(), d.getSum() );
        else
            createAndSaveRemaining( d );
    }

    private void createAndSaveRemaining(DealDetail d) {
        remainingProductsRepository.save(
                new RemainingProducts(
                       d.getProduct(), d.getStorage(), d.getQuantity(), d.getSum(), CURRENT_DATETIME_POINT )
        );
        em.flush();
    }

    private void addQntAndSumTo(RemainingProducts r, Integer qnt, BigDecimal sum) {
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
                remainingProductsRepository.findPrevDateTimePoint();

        if ( prevPoint.compareTo( newPoint ) > 0 )
            throw new IllegalArgumentException("Specified DateTimePoint is before previous");

        List<RemainingProducts> newRemainings =
                remainingProductsRepository.createForNewPoint( prevPoint, newPoint );

        if ( !CURRENT_DATETIME_POINT.equals( prevPoint ) ) {
            addRemainingsFromPrevPoint( newRemainings, prevPoint );
        }

        newRemainings.forEach( p -> p.setDateTimePoint( newPoint ));
        remainingProductsRepository.save( newRemainings );
    }

    private void addRemainingsFromPrevPoint( List<RemainingProducts> newRemainings,
                                             LocalDateTime prevPoint ) {

        List<RemainingProducts> prevRemainings =
                remainingProductsRepository.findByDateTimePoint(prevPoint);

        merge(newRemainings, prevRemainings);
    }

    private void merge( List<RemainingProducts> newRems,
                        List<RemainingProducts> prevRems ) {

        Map<Long, RemainingProducts> matched =
                changeQntAndSumFromPrevRems( newRems, prevRems );

        addPreviousRemainingsNotExistedIn(newRems, prevRems, matched);
    }

    /**
     * Changes quantity and sum of every matched new Remaining with previous Remaining.
     * @param newRems - list of new point Remainings
     * @param prevRems - list of previous point Remainings
     * @return Map of all previous Remainings that have been used for changing new Remainings
     */
    private Map<Long, RemainingProducts> changeQntAndSumFromPrevRems(List<RemainingProducts> newRems,
                                                                     final List<RemainingProducts> prevRems) {
        final Map<Long, RemainingProducts> matched = new HashMap<>();
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
    private void addPreviousRemainingsNotExistedIn(List<RemainingProducts> newRems,
                                                   List<RemainingProducts> prevRems,
                                                   final Map<Long, RemainingProducts> matched) {
        newRems.addAll(
                prevRems.stream()
                        .filter(r -> matched.get(r.getId()) == null)
                        .map(RemainingProducts::new)
                        .collect(Collectors.toList()));
    }

    @Override
    public void deleteAll() {
        this.remainingProductsRepository.deleteAll();
    }

}
