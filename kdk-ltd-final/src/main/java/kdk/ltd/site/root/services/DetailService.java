package kdk.ltd.site.root.services;

import kdk.ltd.site.root.entities.Detail;

import java.util.Collection;

public interface DetailService<T extends Detail> {
    void save(T detail);
    void saveAll(Collection<T> details);
}
