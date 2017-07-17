package kdk.ltd.site.root.services.impl;


import com.mysema.query.BooleanBuilder;
import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.entities.QDealDetail;
import kdk.ltd.site.root.entities.QFactDeal;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.services.DealSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import kdk.ltd.site.root.dto.DealCriteria;
import kdk.ltd.site.root.dto.DetailCriteria;

import javax.inject.Inject;
import java.util.List;

@Service
public class DealSearchQueryDslService implements DealSearchService<Deal> {

    @Inject
    private DealRepository repository;

    @Override
    public Page<Deal> search(DealSearchCriteria criteria, Pageable pageable) {
        BooleanBuilder builder = this.createPredicate( criteria );
        return this.repository.findAll(builder, pageable);
    }

    private BooleanBuilder createPredicate(DealSearchCriteria criteria) {

        BooleanBuilder builder = new BooleanBuilder();

        this.addDealPredicate(
                criteria.getDealCriteria(),
                builder
        );

        this.addDetailPredicate(
                criteria.getDetailCriteria(),
                builder
        );

        return builder;
    }


    private void addDealPredicate(DealCriteria criteria, BooleanBuilder builder) {

        if (criteria == null) return;

        QFactDeal deal = QFactDeal.factDeal;

        if ( criteria.getDealTypes().size() > 0) {
            builder.and(
                    deal.type.in(criteria.getDealTypes())
            );
        }

        if ( criteria.getFrom() != null ) {
            builder.and(deal.dateOfDeal.after(criteria.getFrom()));
        }

        if ( criteria.getTo() != null ) {
            builder.and(
                    deal.dateOfDeal.before(criteria.getTo())
            );
        }

        List<Long> partnerList = criteria.getPartnerIdList();
        if ( partnerList != null && partnerList.size() > 0 && partnerList.get(0) > 0) {
            builder.and(
                    deal.partner.id.in(partnerList)
            );
        }
    }

    private void addDetailPredicate(DetailCriteria criteria, BooleanBuilder builder) {

        if (criteria == null) return;

        QDealDetail detail = QDealDetail.dealDetail;

        List<Long> productList = criteria.getProductIdList();
        if ( productList != null && productList.size() > 0 && productList.get(0) > 0) {
            builder.and(
                    detail.product.id.in( productList )
            );

        }

        List<Long> storageList = criteria.getStorageIdList();
        if ( storageList != null &&  storageList.size() > 0 && storageList.get(0) > 0) {
            builder.and(
                    detail.storage.id.in( storageList )
            );
        }
    }
}
