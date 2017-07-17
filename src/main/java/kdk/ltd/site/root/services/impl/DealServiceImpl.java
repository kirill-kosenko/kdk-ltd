package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.FactDealDTO;
import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.services.DealSearchService;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kdk.ltd.site.root.services.ProductInStockService;

import javax.inject.Inject;

import java.util.LinkedList;
import java.util.List;


@Transactional
@Service
public class DealServiceImpl implements DealService<Deal, FactDealDTO> {

    @Inject
    private DealRepository dealRepository;

    @Inject
    private ProductInStockService productInStockService;

    @Inject
    private DealSearchService<Deal> searchService;

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
    public FactDealDTO findDto(Long id) {
        Deal deal = dealRepository.findOne(id);
        return buildDTO(deal);
    }

    @Override
    public Page<FactDealDTO> findAll(Pageable pageable) {
        Page<Deal> deals = dealRepository.findAll(pageable);
        return new PageImpl<>(transformDocumentsInDTOs(deals), pageable, deals.getTotalElements());
    }


    protected List<FactDealDTO> transformDocumentsInDTOs(Iterable<Deal> documents) {
        List<FactDealDTO> results = new LinkedList<>();
        for (Deal document: documents) {
            results.add( buildDTO(document) );
        }
        return results;
    }


    protected FactDealDTO buildDTO(Deal document) {
        return FactDealDTO.build(document);
    }

    @Override
    public void save(Deal d) {
        this.dealRepository.save(d);
        productInStockService.updateProductsInStock(d.getDetails());
    }

    @Transactional
    @Override
    public void save(List<Deal> deals) {
        for (Deal d: deals) {
            dealRepository.save(d);
            productInStockService.updateProductsInStock(d.getDetails());
        }
    }

    @Override
    public Page<FactDealDTO> search(DealSearchCriteria criteria, Pageable pageable) {
        Page<Deal> deals = searchService.search(criteria, pageable);
        return new PageImpl<>(transformDocumentsInDTOs(deals), pageable, deals.getTotalElements());
    }
}
