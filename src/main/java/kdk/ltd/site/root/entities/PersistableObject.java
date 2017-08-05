package kdk.ltd.site.root.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;



@MappedSuperclass
//@JsonDeserialize(using = DomainObjectDeserializer.class)
public abstract class PersistableObject {

    @Id
    @GeneratedValue(generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                  @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                  @Parameter(name = "optimizer", value = "hilo"),
                  @Parameter(name = "increment_size", value = "50")
            }
    )
    @Access(value = AccessType.PROPERTY)
    private Long id;                    

    @Version
    private Integer version;

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
}
