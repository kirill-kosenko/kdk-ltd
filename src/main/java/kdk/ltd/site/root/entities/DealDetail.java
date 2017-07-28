package kdk.ltd.site.root.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kdk.ltd.site.root.exceptions.SameSignException;
import kdk.ltd.site.web.deserializers.StorageDeserializer;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "deal_details")
public class DealDetail extends Detail  {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Deal deal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    @JsonDeserialize(using = StorageDeserializer.class)
    private Storage storage;

    public DealDetail() {
    }

    public DealDetail(Product product, Integer quantity, BigDecimal sum) {
        super(product, quantity, sum);
    }

    public DealDetail(Product product, Integer quantity, BigDecimal sum, Storage storage) {
        super(product, quantity, sum);
        this.storage = storage;
    }

    public DealDetail(Product product, Integer quantity, BigDecimal sum, Storage storage, Deal deal) {
        this(product, quantity, sum);
        this.storage = storage;
        this.deal = deal;
    }

    public DealDetail(Integer quantity, BigDecimal sum, Product product, Storage storage) {
        this(product, -1*quantity, sum.negate(), storage);
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DealDetail that = (DealDetail) o;

        if (!deal.getId().equals(that.deal.getId())) return false;
        return storage.getId().equals(that.storage.getId());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + deal.getId().hashCode();
        result = 31 * result + storage.getId().hashCode();
        return result;
    }

    @PrePersist
    @PreUpdate
    private void checkSign() {
        if (getQuantity() > 0 && getSum().signum() > 0 ||
                getQuantity() < 0 && getSum().signum() < 0)
            throw new SameSignException();
    }

    /*
    public void negateSum() {
        this.setSum(getSum().negate());
    }

    public void negateQuantity() {
        this.setQuantity(Math.negateExact(getQuantity()));
    }

    @PostLoad
    private void abs() {
        setQuantity(Math.abs(getQuantity()));
        setSum(getSum().abs());
    }*/

    public static DealDetail inverseQntAndSum(DealDetail d) {
        return new DealDetail(
                d.getProduct(),
                -1 * d.getQuantity(),
                d.getSum().negate(),
                d.getStorage(),
                d.getDeal());
    }

}
