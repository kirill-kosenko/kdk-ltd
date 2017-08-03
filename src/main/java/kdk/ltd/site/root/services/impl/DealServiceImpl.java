package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.exceptions.DealNotFoundException;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.repositories.DealDetailRepository;
import kdk.ltd.site.root.services.DealSearchService;
import kdk.ltd.site.root.services.DealService;
import kdk.ltd.site.root.services.RemainingProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DealServiceImpl implements DealService<Deal> {


    private DealRepository dealRepository;

    private DealDetailRepository detailRepository;

    private DealDetailService detailService;

    private RemainingProductsService remainingProductsService;

    private DealSearchService<Deal> searchService;

    @Inject
    public DealServiceImpl(DealRepository dealRepository,
                           DealDetailRepository detailRepository,
                           DealDetailService detailService,
                           RemainingProductsService remainingProductsService,
                           DealSearchService<Deal> searchService) {
        this.dealRepository = dealRepository;
        this.detailRepository = detailRepository;
        this.detailService = detailService;
        this.remainingProductsService = remainingProductsService;
        this.searchService = searchService;
    }

    protected JpaRepository<Deal, Long> getRepository() {
        return this.dealRepository;
    }

    @Override
    public Deal find(Long id) {
        return dealRepository.findOne(id);
    }

    @Override
    public List<Deal> findAll() {
        return dealRepository.findAllDistinctBy();
    }

    @Override
    public Page<Deal> findAll(Pageable pageable) {
        return dealRepository.findAll(pageable);
    }

    @Override
    public void save(Deal d) {
        this.dealRepository.save(d);
        this.detailService.saveAll(d.getDetails());
      //  remainingProductsService.saveOrUpdate(d.getDetails());
    }

    @Override
    public void save(List<Deal> deals) {
        dealRepository.saveBatch(deals);
        detailService.saveAll(
                deals.stream()
                        .map(Deal::getDetails)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Page<Deal> search(DealSearchCriteria criteria, Pageable pageable) {
        return searchService.search(criteria, pageable);
    }

    @Override
    public void update(Deal deal) {
        List<Long> ids = getInvertedIds(
                            deal.getDetails());

        List<DealDetail> forRemainingsUpdate =
                detailRepository.inverseOldDetails(ids);

        dealRepository.save(deal);

        forRemainingsUpdate.addAll(0, deal.getDetails());
//        remainingProductsService.saveOrUpdate(forRemainingsUpdate);
    }

    @Override
    public void delete(Long id) {
        Deal deal = dealRepository.findOne(id);

        if (deal == null)
            throw new DealNotFoundException();

        List<Long> ids =
                getInvertedIds(deal.getDetails());
        List<DealDetail> forRemainingsDeletion =
                detailRepository.inverseOldDetails(ids);

//        remainingProductsService.saveOrUpdate(forRemainingsDeletion);
        dealRepository.delete(deal);
    }

    private List<Long> getInvertedIds(List<DealDetail> details) {
        return details
                .stream()
                .filter(detail -> detail.getId() != null)
                .map(DealDetail::getId)
                .collect(Collectors.toList());
    }
}
