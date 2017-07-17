package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.GenericDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface GenericDealRepository<T extends GenericDeal> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {
}
