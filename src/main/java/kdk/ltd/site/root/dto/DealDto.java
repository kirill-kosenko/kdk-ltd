package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;
import kdk.ltd.site.root.entities.Deal;

import java.time.LocalDateTime;
import java.util.List;


public class DealDto extends GenericDealDto {

    private Deal.State state;
    private List<DealDetailDto> details;

    public DealDto(Long id, Integer version, String partner, LocalDateTime dateOfDocument, String user, GenericDeal.Type type, Deal.State state) {
        super(id, version, partner, dateOfDocument, user, type);
        this.state = state;
    }

    public DealDto(long id, Integer version, String partner, LocalDateTime dateOf, String user, Deal.State state, GenericDeal.Type type, List<DealDetailDto> details) {
        super(id, version, partner, dateOf, user, type);
        this.state = state;
        this.details = details;
    }

    public Deal.State getState() {
        return state;
    }

    public void setState(Deal.State state) {
        this.state = state;
    }

    public List<DealDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<DealDetailDto> details) {
        this.details = details;
    }

    public static DealDto build(Deal d) {
        return new DealDto(
                d.getId(),
                d.getVersion(),
                d.getPartner().getFullname(),
                d.getDateTimeOfDeal(),
                d.getUsername(),
                d.getState(),
                d.getType(),
                DealDetailDto.buildDtoList(d.getDetails())
        );
    }
}
