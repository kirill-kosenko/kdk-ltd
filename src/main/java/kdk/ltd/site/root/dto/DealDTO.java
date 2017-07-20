package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;
import kdk.ltd.site.root.entities.Deal;

import java.time.LocalDate;
import java.util.List;


public class DealDTO extends GenericDealDTO {

    private String state;
    private List<DealDetailDTO> details;

    public DealDTO(long id, String partner, LocalDate dateOf, String user, String state, GenericDeal.Type type, List<DealDetailDTO> details) {
        super(id, partner, dateOf, user, type);
        this.state = state;
        this.details = details;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DealDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<DealDetailDTO> details) {
        this.details = details;
    }

    public static DealDTO build(Deal d) {
        DealDTO dto = new DealDTO(
                d.getId(),
                d.getPartner().getFullname(),
                d.getDateOfDeal(),
                d.getUsername(),
                d.getState().name(),
                d.getType(),
                DealDetailDTO.buildDtoList(d.getDetails())
        );
        return dto;
    }
}
