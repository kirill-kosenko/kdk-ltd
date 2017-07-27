package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.services.ProductInStockService;
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

    private ProductInStockService productInStockService;

    @Inject
    public DealDetailService(DealDetailRepository detailRepository,
                                DealRepository dealRepository,
                                    ProductInStockService productInStockService) {
        this.detailRepository = detailRepository;
        this.dealRepository = dealRepository;
        this.productInStockService = productInStockService;
    }

    @Override
    public void save(DealDetail detail) {
        this.detailRepository.save(detail);
    }

    @Override
    public void saveAll(Collection<DealDetail> details) {
        this.detailRepository.save(details);
        this.productInStockService.updateProductsInStock(details);
    }

    @Override
    public void save( Long dealId, DealDetail detail ) {
        Deal deal = dealRepository.getOne( dealId );
        detail.setDeal( deal );
        detailRepository.save( detail );
    }

    @Override
    public void update(Long id, DealDetail d) {
        DealDetail u = detailRepository.findOne(id);
        DealDetail inversed = DealDetail.inverseQntAndSum(u);
        productInStockService.updateProductsInStock(Arrays.asList(inversed, d));

        if (d.getQuantity() != null) {
            if ( sameSign(
                    u.getQuantity(), d.getQuantity() ))
                u.setQuantity(d.getQuantity());
            else
                throw new UnsupportedOperationException();
        }
        if (d.getStorage() != null) u.setStorage(d.getStorage());
        if (d.getProduct() != null) u.setProduct(d.getProduct());
        if (d.getSum() != null) {
            if ( sameSign( u.getSum(), d.getSum() ) )
                u.setSum(d.getSum());
            else
                throw new UnsupportedOperationException();
        }
        if (d.getDeal() != null) u.setDeal(d.getDeal());
    }

    private <T extends Number> boolean sameSign(T op1, T op2) {
        double d1 = op1.doubleValue();
        double d2 = op2.doubleValue();
        return d1 < 0 && d2 < 0 ||
                    d1 > 0 && d2 > 0 ||
                        d1 == 0 && d2 == 0;
    }

    @Override
    public void delete(Long id) {

    }
}
