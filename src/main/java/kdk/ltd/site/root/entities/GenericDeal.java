package kdk.ltd.site.root.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kdk.ltd.site.web.deserializers.PartnerDeserializer;
import kdk.ltd.site.root.validation.Date;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@MappedSuperclass
public abstract class GenericDeal extends PersistableObjectAudit {

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    @JsonDeserialize(using = PartnerDeserializer.class)
    private Partner partner;

    @Column(name = "is_paid")
    private boolean paid;


    @Column(name = "date_time_of_deal")
    @JsonProperty("dateOfDeal")
    private LocalDateTime dateTimeOfDeal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login")
    private User user;

    public GenericDeal() {
    }

    public GenericDeal(Type type, Partner partner, boolean paid, LocalDateTime dateTimeOfDeal) {
        this.type = type;
        this.partner = partner;
        this.paid = paid;
        this.dateTimeOfDeal = dateTimeOfDeal;
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

    public LocalDateTime getDateTimeOfDeal() {
        return dateTimeOfDeal;
    }

    public void setDateTimeOfDeal(LocalDateTime dateOfDocument) {
        this.dateTimeOfDeal = dateOfDocument;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericDeal that = (GenericDeal) o;

        if (paid != that.paid) return false;
        if (type != that.type) return false;
        if (!partner.getId().equals(that.partner.getId())) return false;
        if (!dateTimeOfDeal.equals(that.dateTimeOfDeal)) return false;
        return user != null ? user.getId().equals(that.user.getId()) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + partner.getId().hashCode();
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + dateTimeOfDeal.hashCode();
        result = 31 * result + (user != null ? user.getId().hashCode() : 0);
        return result;
    }
}
