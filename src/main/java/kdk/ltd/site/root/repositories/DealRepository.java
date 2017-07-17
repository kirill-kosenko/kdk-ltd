package kdk.ltd.site.root.repositories;

import org.springframework.data.jpa.repository.Query;
import kdk.ltd.site.root.entities.Deal;

import java.time.LocalDate;
import java.util.List;


public interface DealRepository extends GenericDealRepository<Deal> {
    @Query("select d.id from Deal d where d.dateOfDeal between ?1 and ?2")
    List<Long> findIdsBetween(LocalDate from, LocalDate to);

}
