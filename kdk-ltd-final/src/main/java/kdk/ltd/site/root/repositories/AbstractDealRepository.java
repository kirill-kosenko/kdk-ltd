package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface AbstractDealRepository<T extends Deal> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {
}
