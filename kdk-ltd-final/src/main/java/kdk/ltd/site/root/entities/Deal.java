package kdk.ltd.site.root.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kdk.ltd.site.web.deserializers.PartnerDeserializer;
import kdk.ltd.site.root.validation.Date;

import javax.persistence.*;
import java.time.LocalDate;


@MappedSuperclass
public abstract class Deal extends PersistableObjectAudit {

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    @JsonDeserialize(using = PartnerDeserializer.class)
    private Partner partner;

    @Column(name = "is_paid")
    private boolean paid;

    @Date
    @Column(name = "date_of")
    private LocalDate dateOfDeal;

    @ManyToOne
    @JoinColumn(name = "user_login")
    private User user;

    public Deal() {
    }

    public Deal(Type type, Partner partner, boolean paid, LocalDate dateOfDeal) {
        this.type = type;
        this.partner = partner;
        this.paid = paid;
        this.dateOfDeal = dateOfDeal;
    }

    public enum Type {
        PURCHASE, SELL, RETURN, CANCEL, MOVE
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getDateOfDeal() {
        return dateOfDeal;
    }

    public void setDateOfDeal(LocalDate dateOfDocument) {
        this.dateOfDeal = dateOfDocument;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
