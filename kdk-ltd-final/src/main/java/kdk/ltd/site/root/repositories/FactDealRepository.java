package kdk.ltd.site.root.repositories;

import org.springframework.data.jpa.repository.Query;
import kdk.ltd.site.root.entities.FactDeal;

import java.time.LocalDate;
import java.util.List;


public interface FactDealRepository extends AbstractDealRepository<FactDeal> {
    @Query("select d.id from FactDeal d where d.dateOfDeal between ?1 and ?2")
    List<Long> findIdsBetween(LocalDate from, LocalDate to);

}
