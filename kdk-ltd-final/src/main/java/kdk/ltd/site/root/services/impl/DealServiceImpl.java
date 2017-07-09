package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.FactDealDTO;
import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.entities.FactDeal;
import kdk.ltd.site.root.repositories.FactDealRepository;
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
public class DealServiceImpl implements DealService<FactDeal, FactDealDTO> {

    @Inject
    private FactDealRepository factDealRepository;

    @Inject
    private ProductInStockService productInStockService;

    @Inject
    private DealSearchService<FactDeal> searchService;

    protected JpaRepository<FactDeal, Long> getRepository() {
        return this.factDealRepository;
    }

    @Override
    public FactDealDTO find(Long id) {
        FactDeal factDeal = factDealRepository.findOne(id);
        return buildDTO(factDeal);
    }

    @Override
    public Page<FactDealDTO> findAll(Pageable pageable) {
        Page<FactDeal> deals = factDealRepository.findAll(pageable);
        return new PageImpl<>(transformDocumentsInDTOs(deals), pageable, deals.getTotalElements());
    }


    protected List<FactDealDTO> transformDocumentsInDTOs(Iterable<FactDeal> documents) {
        List<FactDealDTO> results = new LinkedList<>();
        for (FactDeal document: documents) {
            results.add( buildDTO(document) );
        }
        return results;
    }


    protected FactDealDTO buildDTO(FactDeal document) {
        return FactDealDTO.build(document);
    }

    @Override
    public void save(FactDeal d) {
        this.factDealRepository.save(d);
        productInStockService.updateProductsInStock(d.getDetails());
    }

    @Transactional
    @Override
    public void save(List<FactDeal> deals) {
        for (FactDeal d: deals) {
            factDealRepository.save(d);
            productInStockService.updateProductsInStock(d.getDetails());
        }
    }

    @Override
    public Page<FactDealDTO> search(DealSearchCriteria criteria, Pageable pageable) {
        Page<FactDeal> deals = searchService.search(criteria, pageable);
        return new PageImpl<>(transformDocumentsInDTOs(deals), pageable, deals.getTotalElements());
    }
}
