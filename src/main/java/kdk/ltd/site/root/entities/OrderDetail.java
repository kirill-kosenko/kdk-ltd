package kdk.ltd.site.root.entities;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "order_detail")
public class OrderDetail extends Detail {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(Product product, Integer quantity, BigDecimal sum) {
        super(product, quantity, sum);
    }

    public OrderDetail(Product product, Integer quantity, BigDecimal sum, Order order) {
        this(product, quantity, sum);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
