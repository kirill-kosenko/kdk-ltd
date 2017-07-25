package kdk.ltd.site.root.entities;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProductStorageWrapper {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    public ProductStorageWrapper() {
    }

    public ProductStorageWrapper(Product product, Storage storage) {
        this.product = product;
        this.storage = storage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductStorageWrapper that = (ProductStorageWrapper) o;

        if (!product.getId().equals(that.product.getId())) return false;
        return storage.getId().equals(that.storage.getId());
    }

    @Override
    public int hashCode() {
        int result = product.getId().hashCode();
        result = 31 * result + storage.getId().hashCode();
        return result;
    }
}
