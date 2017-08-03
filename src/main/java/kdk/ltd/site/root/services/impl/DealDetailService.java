package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.entities.RemainingProducts;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.services.RemainingProductsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.repositories.DealDetailRepository;
import kdk.ltd.site.root.services.DetailService;

import javax.inject.Inject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public void save(DealDetail d) {
        this.detailRepository.save(d);
        this.remainingProductsService.saveOrUpdate(
                Collections.singletonList(
                        new RemainingProducts(d.getProduct(), d.getStorage(), d.getQuantity(), d.getSum())));
    }

    @Override
    public void saveAll(Collection<DealDetail> details) {
        this.detailRepository.save(details);
        List<RemainingProducts> rems = details.stream().map(this::createFrom).collect(Collectors.toList());
        this.remainingProductsService.saveOrUpdate(rems);
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
        remainingProductsService.saveOrUpdate(
                Stream.of(d, inversed)
                        .map(this::createFrom)
                        .collect(Collectors.toList()) );
        detailRepository.save(d);
    }

    @Override
    public void delete(Long id) {

    }

    private RemainingProducts createFrom(DealDetail d) {
        return new RemainingProducts(d.getProduct(), d.getStorage(), d.getQuantity(), d.getSum());
    }
}
