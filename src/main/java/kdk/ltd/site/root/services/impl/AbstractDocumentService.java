package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.DealDTO;
import kdk.ltd.site.root.entities.GenericDeal;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public abstract class AbstractDocumentService<T extends GenericDeal, U extends DealDTO>  implements DealService<T, U> {

    public void save(T document) {
        getRepository().save(document);
    }

    protected List<U> transformDocumentsInDTOs(Iterable<T> documents) {
        List<U> results = new LinkedList<>();
        for (T document: documents) {
            results.add( buildDTO(document) );
        }
        return results;
    }


    protected abstract JpaRepository<T, ? extends Serializable> getRepository();
    protected abstract U buildDTO(T document);

}
