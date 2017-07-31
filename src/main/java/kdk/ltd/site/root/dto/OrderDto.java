package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;
import kdk.ltd.site.root.entities.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class OrderDto extends GenericDealDto {

    private LocalDate completionDate;
    private boolean active;
    private List<DetailDto> details;

    public OrderDto(long id, Integer version, String partner, LocalDateTime dateOf, String user, LocalDate completionDate, boolean active, GenericDeal.Type type, List<DetailDto> details) {
        super(id, version, partner, dateOf, user, type);
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

    public List<DetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<DetailDto> details) {
        this.details = details;
    }

    public static OrderDto build(Order o) {
        OrderDto dto = new OrderDto(
                o.getId(),
                o.getVersion(),
                o.getPartner().getFullname(),
                o.getDateTimeOfDeal(),
                o.getUser().getUsername(),
                o.getCompletionDate(),
                o.isActive(),
                o.getType(),
                DetailDto.buildList(o.getDetails())
        );
        return dto;
    }


}
