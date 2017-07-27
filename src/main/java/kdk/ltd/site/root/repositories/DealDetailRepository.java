package kdk.ltd.site.root.repositories;

import com.querydsl.core.types.Predicate;
import kdk.ltd.site.root.entities.DealDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;


public interface DealDetailRepository extends JpaRepository<DealDetail, Long>, QueryDslPredicateExecutor<DealDetail> {

    List<DealDetail> findAll(Predicate predicate);
}
