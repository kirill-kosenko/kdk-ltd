package kdk.ltd.site.root.repositories;

import com.mysema.query.types.Predicate;
import kdk.ltd.site.root.entities.DealDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;


public interface DetailRepository extends JpaRepository<DealDetail, Long>, QueryDslPredicateExecutor<DealDetail> {
    List<DealDetail> findAll(Predicate predicate);
}
