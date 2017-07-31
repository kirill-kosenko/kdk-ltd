package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.DealDto;
import kdk.ltd.site.root.entities.GenericDeal;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public abstract class
        AbstractDocumentService<T extends GenericDeal>
                                        implements DealService<T> {

    public void save(T document) {
        getRepository().save(document);
    }

    protected abstract JpaRepository<T, ? extends Serializable> getRepository();
}
