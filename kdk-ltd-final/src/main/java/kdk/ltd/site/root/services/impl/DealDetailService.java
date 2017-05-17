package kdk.ltd.site.root.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.repositories.DetailRepository;
import kdk.ltd.site.root.repositories.ProductInStockRepository;
import kdk.ltd.site.root.services.DetailService;

import javax.inject.Inject;

import java.util.Collection;


@Service
@Transactional
public class DealDetailService implements DetailService<DealDetail> {

    @Inject
    private DetailRepository detailRepository;
    @Inject
    private ProductInStockRepository remainingRepository;

    @Override
    public void save(DealDetail detail) {
        this.detailRepository.save(detail);
    }

    @Override
    public void saveAll(Collection<DealDetail> details) {
        this.detailRepository.save(details);
    }
}
