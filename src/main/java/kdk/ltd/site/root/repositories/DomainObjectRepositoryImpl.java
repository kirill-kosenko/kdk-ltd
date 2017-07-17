package kdk.ltd.site.root.repositories;


import kdk.ltd.site.root.entities.PersistableObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Service
public class DomainObjectRepositoryImpl implements DomainObjectRepository<Long, String> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersistableObject find(Long aLong, String classname) {
        Query query = entityManager.createQuery("select o from " + classname + " o where o.id = :id");
        query.setParameter("id", classname);
        return (PersistableObject) query.getSingleResult();
    }
}
