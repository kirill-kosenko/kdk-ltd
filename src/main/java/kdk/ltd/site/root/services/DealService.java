package kdk.ltd.site.root.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kdk.ltd.site.root.dto.DealSearchCriteria;


import java.util.List;


public interface DealService<T, U>  {//extends GenericService<T, Long> {

    T find(Long id);

    List<T> findAll();

    U findDto(Long id);

    Page<U> findAll(Pageable pageable);

    void save(T deal);

    void save(List<T> documents);

    Page<U> search(DealSearchCriteria criteria, Pageable pageable);

    void update(T deal);

    void delete(Long id);
}
