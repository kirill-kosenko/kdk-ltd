package kdk.ltd.site.root.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "products_in_stock")
public class ProductInStock extends PersistableObject implements Serializable {

    public static final LocalDate CURRENT_REMAININGS_DATE = LocalDate.of(2000, 1, 1);

    public ProductInStock() {
    }

    public ProductInStock(Long id, LocalDate restDate, Storage storage, Product product, Integer quantity, BigDecimal sum) {
        super(id);
        this.restDate = restDate;
        this.storage = storage;
        this.product = product;
        this.quantity = quantity;
        this.sum = sum;
    }

    public ProductInStock(Storage storage, Product product, Long qnt, BigDecimal sum) {
        if (qnt > Integer.MAX_VALUE) throw new IllegalArgumentException("Sum of quantity is out of Integer range");
        this.storage = storage;
        this.product = product;
        this.quantity = qnt.intValue();
        this.sum = sum;
    }

    public ProductInStock(LocalDate restDate, Storage storage, Product product, Integer quantity, BigDecimal sum) {
        this.restDate = restDate;
        this.storage = storage;
        this.product = product;
        this.quantity = quantity;
        this.sum = sum;
    }

    private LocalDate restDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "qnt")
    private Integer quantity;

    @Column(name = "summ")
    private BigDecimal sum;

    public LocalDate getRestDate() {
        return restDate;
    }

    public void setRestDate(LocalDate restDate) {
        this.restDate = restDate;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
}
