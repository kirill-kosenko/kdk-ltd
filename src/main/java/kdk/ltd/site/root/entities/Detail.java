package kdk.ltd.site.root.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kdk.ltd.site.web.deserializers.ProductDeserializer;

import javax.persistence.*;
import java.math.BigDecimal;


@MappedSuperclass
public abstract class Detail extends PersistableObjectAudit {

    public Detail() {
    }

    public Detail(Product product, Integer quantity, BigDecimal sum) {
        this.product = product;
        this.quantity = quantity;
        this.sum = sum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonDeserialize(using = ProductDeserializer.class)
    private Product product;

    @Column(name = "qnt")
    private Integer quantity;

    @Column(name = "summ")
    private BigDecimal sum;

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

    public void setStorage() {

    }

    public Storage getStorage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detail detail = (Detail) o;

        if (!product.getId().equals(detail.product.getId())) return false;
        if (!quantity.equals(detail.quantity)) return false;
        return sum.equals(detail.sum);
    }

    @Override
    public int hashCode() {
        int result = product.getId().hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + sum.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return  "product=" + product.getId() +
                ", quantity=" + quantity +
                ", sum=" + sum;
    }
}
