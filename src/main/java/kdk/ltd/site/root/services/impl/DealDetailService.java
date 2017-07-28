package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.services.RemainingProductsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.repositories.DealDetailRepository;
import kdk.ltd.site.root.services.DetailService;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Collection;


@Service
@Transactional
public class DealDetailService implements DetailService<DealDetail> {

    private DealDetailRepository detailRepository;

    private DealRepository dealRepository;

    private RemainingProductsService remainingProductsService;

    @Inject
    public DealDetailService(DealDetailRepository detailRepository,
                                DealRepository dealRepository,
                                    RemainingProductsService remainingProductsService) {
        this.detailRepository = detailRepository;
        this.dealRepository = dealRepository;
        this.remainingProductsService = remainingProductsService;
    }

    @Override
    public void save(DealDetail detail) {
        this.detailRepository.save(detail);
    }

    @Override
    public void saveAll(Collection<DealDetail> details) {
        this.detailRepository.save(details);
        this.remainingProductsService.update(details);
    }

    @Override
    public void save( Long dealId, DealDetail detail ) {
        Deal deal = dealRepository.getOne( dealId );
        detail.setDeal( deal );
        detailRepository.save( detail );
    }

    @Override
    public void update(DealDetail d) {
        DealDetail inversed = DealDetail.inverseQntAndSum(d);
        remainingProductsService.update( Arrays.asList(inversed, d) );
        detailRepository.save(d);
    }

    @Override
    public void delete(Long id) {

    }
}
