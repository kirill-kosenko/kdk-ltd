package kdk.ltd.site.root.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@MappedSuperclass
//@JsonDeserialize(using = DomainObjectDeserializer.class)
public abstract class PersistableObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(value = AccessType.PROPERTY)
    private Long id;

    @Version
    private Integer version;

    /*public PersistableObject(Long id, Integer version) {
        this.id = id;
        this.version = version;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long pkey) {
        this.id = pkey;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PersistableObject() {
    }

    public PersistableObject(Long id) {
        this.id = id;
    }
}
