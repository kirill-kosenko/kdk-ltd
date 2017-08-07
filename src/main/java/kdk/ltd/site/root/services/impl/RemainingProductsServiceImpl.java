package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.*;
import kdk.ltd.site.root.repositories.RemainingProductsRepository;
import kdk.ltd.site.root.services.RemainingProductsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class RemainingProductsServiceImpl implements RemainingProductsService {

    private final LocalDateTime CURRENT_DATETIME_POINT;

    private RemainingProductsRepository remainingProductsRepository;

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
    public void saveOrUpdate(Collection<RemainingProducts> list) {
        Collection<RemainingProducts> merged =
                mergeByProductAndStorage(list);

        merged.forEach(this::saveOrUpdate);
    }

    private Collection<RemainingProducts>
    mergeByProductAndStorage(Collection<RemainingProducts> list) {

        final Map<Integer, RemainingProducts> merged = new HashMap<>();

        list.forEach(rmp -> {
                    Integer hash = rmp.productStorageHash();
                    RemainingProducts r = merged.get( hash );
                    if (r == null) {
                        merged.put(hash, rmp);
                    } else {
                        addQntAndSumTo(r, rmp.getQuantity(), rmp.getSum());
                    }
                }
        );
        return merged.values();
    }

    private void saveOrUpdate(RemainingProducts rmp) {

        Optional<RemainingProducts> inStock =
                    remainingProductsRepository.findByProductAndStorageAndDateTimePoint(
                            rmp.getProduct().getId(), rmp.getStorage().getId(), CURRENT_DATETIME_POINT
                    );

        if (inStock.isPresent())
            addQntAndSumTo( inStock.get(), rmp.getQuantity(), rmp.getSum() );
        else {
            rmp.setDateTimePoint(CURRENT_DATETIME_POINT);
            remainingProductsRepository.save(rmp);
        }
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
    public void createNewDateTimePoint(final LocalDateTime newDateTimePoint) {

        final LocalDateTime prevDateTimePoint =
                remainingProductsRepository.findPrevDateTimePoint();

        if ( prevDateTimePoint.compareTo( newDateTimePoint ) > 0 )
            throw new IllegalArgumentException("Specified DateTimePoint is before previous one");

        List<RemainingProducts> newRemainings =
                remainingProductsRepository.createForNewPoint( prevDateTimePoint, newDateTimePoint );

        if ( !CURRENT_DATETIME_POINT.equals( prevDateTimePoint ) ) {
            addRemainingsFromPrevPoint( newRemainings, prevDateTimePoint );
        }

        newRemainings.forEach( p -> p.setDateTimePoint( newDateTimePoint ));
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
