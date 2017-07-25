package kdk.ltd.site.root.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class InStockId implements Serializable {

    @Embedded
    private ProductStorageWrapper productStorageWrapper;

    @Column(name = "restDate")
    private LocalDateTime restDateTime;

    public InStockId() {
    }

    public InStockId(Product product, Storage storage) {
        this.productStorageWrapper = new ProductStorageWrapper(product, storage);
    }

    public InStockId(ProductStorageWrapper productStorageWrapper, LocalDateTime restDateTime) {
        this.productStorageWrapper = productStorageWrapper;
        this.restDateTime = restDateTime;
    }

    public InStockId(Product product, Storage storage, LocalDateTime dateTime) {
        this.productStorageWrapper = new ProductStorageWrapper(product, storage);
        this.restDateTime = dateTime;
    }

    public ProductStorageWrapper getProductStorageWrapper() {
        return productStorageWrapper;
    }

    public void setProductStorageWrapper(ProductStorageWrapper productStorageWrapper) {
        this.productStorageWrapper = productStorageWrapper;
    }

    public LocalDateTime getRestDateTime() {
        return restDateTime;
    }

    public void setRestDateTime(LocalDateTime restDate) {
        this.restDateTime = restDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InStockId inStockId = (InStockId) o;

        if (!productStorageWrapper.equals(inStockId.productStorageWrapper)) return false;
        return restDateTime.equals(inStockId.restDateTime);
    }

    @Override
    public int hashCode() {
        int result = productStorageWrapper.hashCode();
        result = 31 * result + restDateTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InStockId{" +
                "product=" + productStorageWrapper.getProduct() +
                ", storage=" + productStorageWrapper.getStorage() +
                ", restDate=" + restDateTime +
                '}';
    }
}
