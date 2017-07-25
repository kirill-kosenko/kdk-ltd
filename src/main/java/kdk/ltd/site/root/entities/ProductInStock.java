package kdk.ltd.site.root.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "products_in_stock")
public class ProductInStock implements Serializable {

    @EmbeddedId
    private InStockId id;

    public ProductInStock() {
    }

    public ProductInStock(InStockId id, Integer quantity, BigDecimal sum) {
        this.id = id;
        this.quantity = quantity;
        this.sum = sum;
    }

    public ProductInStock(Product product, Storage storage, Integer quantity, BigDecimal sum) {
        this.id = new InStockId(product, storage);
        this.quantity = quantity;
        this.sum = sum;
    }

    public ProductInStock(LocalDateTime restDate, Storage storage, Product product, Integer quantity, BigDecimal sum) {
        this.id = new InStockId(product, storage, restDate);
        this.quantity = quantity;
        this.sum = sum;
    }


    @Column(name = "qnt")
    private Integer quantity;

    @Column(name = "summ")
    private BigDecimal sum;

    public InStockId getId() {
        return id;
    }

    public void setId(InStockId id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public ProductInStock addQnt(Integer qnt) {
        this.setQuantity(quantity + qnt);
        return this;
    }

    public ProductInStock addSumm(BigDecimal sum) {
        this.setSum(this.sum.add(sum));
        return this;
    }

    public ProductInStock subQnt(Integer qnt) {
        this.setQuantity(quantity - qnt);
        return this;
    }

    public ProductInStock subSum(BigDecimal sum) {
        this.setSum(this.sum.subtract(sum));
        return this;
    }

    //delegation
    public ProductStorageWrapper getProductStorageWrapper() {
        return id.getProductStorageWrapper();
    }

    public void setProductStorageWrapper(ProductStorageWrapper productStorageWrapper) {
        id.setProductStorageWrapper(productStorageWrapper);
    }

    public LocalDateTime getRestDateTime() {
        return id.getRestDateTime();
    }

    public void setRestDateTime(LocalDateTime restDate) {
        id.setRestDateTime(restDate);
    }
}
