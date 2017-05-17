package kdk.ltd.site.root.entities;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "storages")
public class Storage extends PersistableObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Storage{" +
          //      "name='" + name + '\'' +
                '}';
    }
}
