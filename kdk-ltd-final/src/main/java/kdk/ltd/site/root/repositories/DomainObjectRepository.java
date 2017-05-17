package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.PersistableObject;

import java.io.Serializable;

public interface DomainObjectRepository<ID extends Serializable, E extends String> {
    PersistableObject find(ID id, E classname);
}
