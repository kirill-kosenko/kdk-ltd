package kdk.ltd.site.root.repositories;

import org.springframework.data.jpa.repository.Query;
import kdk.ltd.site.root.entities.Deal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface DealRepository extends GenericDealRepository<Deal>, DealRepositoryCustom {
    @Query("select d.id from Deal d where d.dateTimeOfDeal between ?1 and ?2")
    List<Long> findIdsBetween(LocalDateTime from, LocalDateTime to);

}
