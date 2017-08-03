package kdk.ltd.site.root.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.Version;



@MappedSuperclass
//@JsonDeserialize(using = DomainObjectDeserializer.class)
public abstract class PersistableObject {

    @Id
    @GeneratedValue(generator = "optimized-sequence")
    @Access(value = AccessType.PROPERTY)
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                  @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                  @Parameter(name = "optimizer", value = "hilo"),
                  @Parameter(name = "increment_size", value = "50")
            }
    )
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
