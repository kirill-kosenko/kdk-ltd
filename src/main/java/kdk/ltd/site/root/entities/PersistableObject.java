package kdk.ltd.site.root.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@MappedSuperclass
//@JsonDeserialize(using = DomainObjectDeserializer.class)
public abstract class PersistableObject {

    @Id
    @GenericGenerator(
            name = "sequenceGenerator",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "optimizer",
                            value = "pooled-lo"),
                    @org.hibernate.annotations.Parameter(
                            name = "initial_value",
                            value = "1"),
                    @org.hibernate.annotations.Parameter(
                            name = "increment_size",
                            value = "50"
                    )
            }
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceGenerator")

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
