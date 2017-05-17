package kdk.ltd.site.root.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@MappedSuperclass
//@JsonDeserialize(using = DomainObjectDeserializer.class)
public abstract class PersistableObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Long id;

    @JsonIgnore
    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    protected void setId(Long pkey) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistableObject that = (PersistableObject) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
