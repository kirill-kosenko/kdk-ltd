package kdk.ltd.site.root.services;

import java.io.Serializable;
import java.util.List;


public interface GenericService<T, ID extends Serializable> {

    public T findOne(ID id);

    public List<T> findAll();

    public void save(T object);

    public void saveList(List<T> list);

    public void delete(ID key);

    public void update(ID id, T object);
}
