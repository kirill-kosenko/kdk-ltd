package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.DealDTO;
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
public class DealServiceImpl implements DealService<Deal, DealDTO> {


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
        return dealRepository.findAll();
    }

    @Override
    public DealDTO findDto(Long id) {
        Deal deal = dealRepository.findOne(id);
        return buildDTO(deal);
    }

    @Override
    public Page<DealDTO> findAll(Pageable pageable) {
        Page<Deal> deals = dealRepository.findAll(pageable);
        return new PageImpl<>(transformDocumentsInDTOs(deals), pageable, deals.getTotalElements());
    }


    protected List<DealDTO> transformDocumentsInDTOs(Iterable<Deal> documents) {
        List<DealDTO> results = new LinkedList<>();
        for (Deal document: documents) {
            results.add( buildDTO(document) );
        }
        return results;
    }


    protected DealDTO buildDTO(Deal document) {
        return DealDTO.build(document);
    }

    @Override
    public void save(Deal d) {
        this.dealRepository.save(d);
        remainingProductsService.update(d.getDetails());
    }

    @Transactional
    @Override
    public void save(List<Deal> deals) {
        dealRepository.saveBatch(deals);
        List<DealDetail> details = new LinkedList<>();
        deals.forEach(d -> details.addAll(d.getDetails()));
        remainingProductsService.update(details);
    }


    @Override
    public Page<DealDTO> search(DealSearchCriteria criteria, Pageable pageable) {
        Page<Deal> deals = searchService.search(criteria, pageable);
        return new PageImpl<>(transformDocumentsInDTOs(deals), pageable, deals.getTotalElements());
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
