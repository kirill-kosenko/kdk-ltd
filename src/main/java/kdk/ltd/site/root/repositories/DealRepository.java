package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.dto.DealDTO;
import kdk.ltd.site.root.entities.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealRepository extends GenericDealRepository<Deal>, DealRepositoryCustom {

    @EntityGraph(value = "graph.document.details")
    public Deal findOne(Long id);

    @EntityGraph(value = "graph.document.details")
    public List<Deal> findAll();

    @EntityGraph(value = "graph.document.details")
    public Page<Deal> findAll(Pageable pageable);
}
