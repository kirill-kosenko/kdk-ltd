package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.Product;
import kdk.ltd.site.root.entities.Storage;


public class Remnant {
    private Product product;
    private Storage storage;
    private Long quantity;

    public Remnant() {
    }

    public Remnant(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Remnant(Product product, Storage storage, Long quantity) {
        this.product = product;
        this.storage = storage;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
