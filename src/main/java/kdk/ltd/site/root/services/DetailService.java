package kdk.ltd.site.root.services;

import kdk.ltd.site.root.entities.Detail;
import kdk.ltd.site.root.entities.GenericDeal;

import java.util.Collection;

public interface DetailService<T extends Detail> {
    void save( T detail );
    void save( Long dealId, T detail );
    void saveAll( Collection<T> details );
    void update( T detail );
    void update( Collection<T> details );
    void delete( Long id );
    void delete( Collection<T> details );
}
