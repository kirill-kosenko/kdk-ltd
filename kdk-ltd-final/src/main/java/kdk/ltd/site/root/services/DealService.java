package kdk.ltd.site.root.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kdk.ltd.site.root.dto.DealSearchCriteria;


import java.util.List;


public interface DealService<T, U>  {//extends GenericService<T, Long> {
    U find(Long id);
    Page<U> findAll(Pageable pageable);
    void save(T document);
    void save(List<T> documents);
    Page<U> search(DealSearchCriteria criteria, Pageable pageable);




}
