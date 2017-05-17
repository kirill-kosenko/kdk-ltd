package kdk.ltd.site.root.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "orders")
public class Order extends Deal {

    private LocalDate completionDate;
    private boolean active;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();

    public Order() {
    }

    public Order(Type type, Partner partner, boolean paid, LocalDate dateOfDocument, boolean active) {
        super(type, partner, paid, dateOfDocument);
        this.active = active;
    }

    public Order(Type type, Partner partner, boolean paid, LocalDate dateOfDocument, boolean active, List<OrderDetail> details) {
        this(type, partner, paid, dateOfDocument, active);
        this.details = details;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public void add(OrderDetail detail) {
        detail.setOrder(this);
        this.details.add(detail);
    }

    public void addAll(Collection<OrderDetail> details) {
        this.details.addAll(
                details.stream()
                        .peek(d -> d.setOrder(this))
                        .collect(Collectors.toList())
        );
    }

    @PrePersist
    @PreUpdate
    private void pre() {
        this.details.stream().peek(d -> d.setOrder(this)).collect(Collectors.toList());
    }
}
