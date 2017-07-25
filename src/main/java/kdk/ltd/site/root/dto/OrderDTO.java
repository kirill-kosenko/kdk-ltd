package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;
import kdk.ltd.site.root.entities.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class OrderDTO extends GenericDealDTO {

    private LocalDate completionDate;
    private boolean active;
    private List<DetailDTO> details;

    public OrderDTO(long id, String partner, LocalDateTime dateOf, String user, LocalDate completionDate, boolean active, GenericDeal.Type type, List<DetailDTO> details) {
        super(id, partner, dateOf, user, type);
        this.completionDate = completionDate;
        this.active = active;
        this.details = details;
    }



    public LocalDate getComlpetionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate comletionDate) {
        this.completionDate = comletionDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<DetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<DetailDTO> details) {
        this.details = details;
    }

    public static OrderDTO build(Order o) {
        OrderDTO dto = new OrderDTO(
                o.getId(),
                o.getPartner().getFullname(),
                o.getDateTimeOfDeal(),
                o.getUser().getUsername(),
                o.getCompletionDate(),
                o.isActive(),
                o.getType(),
                DetailDTO.buildList(o.getDetails())
        );
        return dto;
    }


}
