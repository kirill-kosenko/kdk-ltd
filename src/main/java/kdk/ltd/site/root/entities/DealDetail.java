package kdk.ltd.site.root.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kdk.ltd.site.web.deserializers.StorageDeserializer;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "deal_details")
public class DealDetail extends Detail  {

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Deal document;

    @ManyToOne
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

    public DealDetail(Product product, Integer quantity, BigDecimal sum, Storage storage, Deal document) {
        this(product, quantity, sum);
        this.storage = storage;
        this.document = document;
    }

    public Deal getDocument() {
        return document;
    }

    public void setDocument(Deal document) {
        this.document = document;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof DealDetail)) return false;
        DealDetail obj = (DealDetail) o;
        return this.getId().equals(obj.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

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
    }


}
