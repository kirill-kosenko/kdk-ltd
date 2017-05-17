package kdk.ltd.site.root.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "partners")
public class Partner extends PersistableObject {

    private String fullname;
    private String firstname;
    private String lastname;
    private String fathername;
    private String name;


    @OneToMany(mappedBy = "partner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones = new LinkedList<>();

    public Partner() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        this.phones.add(phone);
    }

    @Transient
    private List<String> phoneNumbers = new ArrayList<>();

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
