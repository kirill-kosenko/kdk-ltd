package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.services.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


public abstract class AbstractGenericService<T, ID extends Serializable>
                        implements GenericService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public AbstractGenericService() {
    }

    @Override
    @Transactional
    public T findOne(ID id) {
        return getRepository().findOne(id);
    }

    @Override
    @Transactional
    public List<T> findAll() {
        return (List<T>) this.getRepository().findAll();
    }

    @Override
    @Transactional
    public void save(T object) {
        getRepository().save(object);
    }

    @Override
    @Transactional
    public void saveList(List<T> list) {
        getRepository().save(list);
    }

    @Override
    @Transactional
    public void delete(ID object) {
        getRepository().delete(object);
    }

    @Transactional
    public void update(ID key, T source) {
        throw new UnsupportedOperationException("Update should be overriden");
    }
}
