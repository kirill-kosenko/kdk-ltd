package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.DealDto;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DealServiceImpl implements DealService<Deal> {


    private DealRepository dealRepository;

    private DealDetailRepository detailRepository;

    private RemainingProductsService remainingProductsService;

    private DealSearchService<Deal> searchService;

    @Inject
    public DealServiceImpl(DealRepository dealRepository,
                           DealDetailRepository detailRepository,
                           RemainingProductsService remainingProductsService,
                           DealSearchService<Deal> searchService) {
        this.dealRepository = dealRepository;
        this.detailRepository = detailRepository;
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
        remainingProductsService.update(d.getDetails());
    }

    @Override
    public void save(List<Deal> deals) {
        dealRepository.saveBatch(deals);
        List<DealDetail> details = new LinkedList<>();
        deals.forEach(d -> details.addAll(d.getDetails()));
        remainingProductsService.update(details);
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

        forRemainingsUpdate.addAll(deal.getDetails());
        remainingProductsService.update(forRemainingsUpdate);
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

        remainingProductsService.update(forRemainingsDeletion);
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
