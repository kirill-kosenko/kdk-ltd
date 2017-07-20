package kdk.ltd.site.root.entities;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class InStockId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    private LocalDate restDate;

    public InStockId() {
    }

    public InStockId(Product product, Storage storage, LocalDate date) {
        this.product = product;
        this.storage = storage;
        this.restDate = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public LocalDate getRestDate() {
        return restDate;
    }

    public void setRestDate(LocalDate restDate) {
        this.restDate = restDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InStockId inStockId = (InStockId) o;

        if (!product.getId().equals(inStockId.product.getId())) return false;
        if (!storage.getId().equals(inStockId.storage.getId())) return false;
        return restDate.equals(inStockId.restDate);

    }

    @Override
    public int hashCode() {
        int result = product.getId().hashCode();
        result = 31 * result + storage.getId().hashCode();
        result = 31 * result + restDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InStockId{" +
                "product=" + product +
                ", storage=" + storage +
                ", restDate=" + restDate +
                '}';
    }
}
