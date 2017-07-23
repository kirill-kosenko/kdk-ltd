package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.Deal;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

public class DealRepositoryImpl implements DealRepositoryCustom {

    private final static int BATCH_SIZE = 50;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveBatch(List<Deal> deals) {
        em.setFlushMode(FlushModeType.COMMIT);

        for (int i = 0; i < deals.size(); i++) {
            em.persist(deals.get(i));
            if (i % BATCH_SIZE == 0 && i > 0) {
                em.flush();
                em.clear();
            }
        }
    }
}
