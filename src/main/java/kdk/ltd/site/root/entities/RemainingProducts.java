package kdk.ltd.site.root.entities;

import kdk.ltd.site.root.exceptions.NegativeBalanceException;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;


@Entity
@Table(name = "products_in_stock")
public class RemainingProducts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restDate")
    private LocalDateTime dateTimePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Column(name = "qnt")
    private Integer quantity;

    @Column(name = "summ")
    private BigDecimal sum;

    public RemainingProducts() {
    }

    public RemainingProducts(Product product, Storage storage, int quantity, BigDecimal sum) {
        setProduct(product);
        setStorage(storage);
        setQuantity(quantity);
        setSum(sum);
    }

    public RemainingProducts(Product product, Storage storage, BigDecimal sum, Long quantity) {
        this(product, storage, quantity.intValue(), sum);
    }

    public RemainingProducts(Product product, Storage storage, int quantity, BigDecimal sum, LocalDateTime dateTimePoint) {
        this(product, storage, quantity, sum);
        setDateTimePoint(dateTimePoint);
    }

    public RemainingProducts(RemainingProducts r) {
        this(r.getProduct(), r.getStorage(), r.getQuantity(), r.getSum());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTimePoint() {
        return dateTimePoint;
    }

    public void setDateTimePoint(LocalDateTime restDateTime) {
        this.dateTimePoint = restDateTime;
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

    public boolean eqaulsByProductAndStorage(RemainingProducts p) {
        return !product.getId().equals(p.getProduct().getId()) &&
                !storage.getId().equals(p.getStorage().getId());
    }

    @PrePersist
    @PreUpdate
    private void checkQuantity() {
        if (quantity < 0)
            throw new NegativeBalanceException(
                    String.format("Product: %d Storage: %d Qnt: %d Sum: %s" ,
                            getProduct().getId(),
                            getStorage().getId(),
                            quantity,
                            NumberFormat.getCurrencyInstance().format(sum))
            );
    }

}
