package kdk.ltd.site.root.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone extends PersistableObjectAudit {

    private String code;

    private String number;

    private String fullNumber;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    @JsonIgnore
    private Partner partner;

    @PrePersist
    @PreUpdate
    private void populateFullnumber() {
        this.fullNumber = this.code + this.number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public void setFullNumber(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
