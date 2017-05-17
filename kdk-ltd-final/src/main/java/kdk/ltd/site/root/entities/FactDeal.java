package kdk.ltd.site.root.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;


@Entity
@NamedEntityGraph(name = "graph.document.details",
        attributeNodes = @NamedAttributeNode("details")
)
@Table(name = "deals")
public class FactDeal extends Deal {

    private State state;

    @OneToMany(mappedBy = "document", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<DealDetail> details = new ArrayList<>();

    public enum State {
        STARTED, SENT, RECEIVED, COMPLETED, CANCELED
    }

    public FactDeal() {
    }

    public FactDeal(Type type, Partner partner, boolean paid, LocalDate dateOfDocument) {
        super(type, partner, paid, dateOfDocument);
    }

    public FactDeal(Type type, Partner partner, boolean paid, LocalDate dateOfDocument, List<DealDetail> details) {
        this(type, partner, paid, dateOfDocument);
        this.details = details;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<DealDetail> getDetails() {
        return details;
    }

    public void setDetails(List<DealDetail> details) {
        this.details = details;
    }

    public void addDetail(DealDetail detail) {
        this.details.add(detail);
        detail.setDocument(this);
    }

    public void addAllDetails(Collection<DealDetail> details) {
        for (DealDetail detail: details) {
            detail.setDocument(this);
        }
        this.getDetails().addAll(details);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof FactDeal)) return false;
        FactDeal obj = (FactDeal) o;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @PrePersist
    @PreUpdate
    private void pre() {
        switch (getType()) {
                case PURCHASE:
                case RETURN:
                    changeInOutDetails(DealDetail::negateSum);
                    break;
                case SELL:
                case CANCEL:
                    changeInOutDetails(DealDetail::negateQuantity);
                    break;
                case MOVE:
                    changeMoveDetails();
            }
        }


    private void changeInOutDetails(Consumer<DealDetail> c) {
        details.forEach(d -> {d.setDocument(this); c.accept(d); } );
    }

    private void changeMoveDetails() {
        IntStream.range(0, details.size()).forEach((idx) -> changeMove(idx, details.get(idx)));
    }

    private void changeMove(int i, DealDetail d) {
       if (i%2 == 0) d.negateQuantity();
       else d.negateSum();
       d.setDocument(this);
    }
}
