package kdk.ltd.site.root.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@NamedEntityGraph(name = "graph.document.details",
        attributeNodes = {
                @NamedAttributeNode(value = "details",
                                    subgraph = "subgraph.deal.details"),
                @NamedAttributeNode("partner")
        },
        subgraphs = @NamedSubgraph(name = "subgraph.deal.details",
                            attributeNodes = {
                                @NamedAttributeNode("product"),
                                @NamedAttributeNode("storage")
                            }
        )
)
@Table(name = "deals")
@DynamicUpdate
public class Deal extends GenericDeal {

    private State state;

    @OneToMany(mappedBy = "deal", fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<DealDetail> details = new ArrayList<>();

    public enum State {
        STARTED, SENT, RECEIVED, COMPLETED, CANCELED
    }

    public Deal() {
    }

    public Deal(Type type, Partner partner, boolean paid, LocalDateTime dateOfDocument) {
        super(type, partner, paid, dateOfDocument);
    }

    public Deal(Type type, Partner partner, boolean paid, LocalDateTime dateOfDocument, List<DealDetail> details) {
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
        details.forEach(d -> d.setDeal(this));
        this.details = details;
    }

    public void add(DealDetail detail) {
        this.details.add(detail);
        detail.setDeal(this);
    }

    public void addAll(Collection<DealDetail> details) {
        details.forEach(d -> d.setDeal(this));
        this.getDetails().addAll(details);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Deal deal = (Deal) o;

        if (state != deal.state) return false;
        return details.equals(deal.details);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + details.hashCode();
        return result;
    }
}
