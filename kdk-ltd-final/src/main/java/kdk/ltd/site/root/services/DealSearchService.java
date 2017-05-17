package kdk.ltd.site.root.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kdk.ltd.site.root.dto.DealSearchCriteria;


public interface DealSearchService<T> {
    Page<T> search(DealSearchCriteria criteria, Pageable pageable);
}
