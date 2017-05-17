package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.entities.FactDeal;

import java.time.LocalDate;
import java.util.List;


public class FactDealDTO extends DealDTO {

    private String state;
    private List<DealDetailDTO> details;

    public FactDealDTO(long id, String partner, LocalDate dateOf, String user, String state, Deal.Type type, List<DealDetailDTO> details) {
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

    public static FactDealDTO build(FactDeal d) {
        FactDealDTO dto = new FactDealDTO(
                d.getId(),
                d.getPartner().getName(),
                d.getDateOfDeal(),
                d.getUsername(),
                d.getState().name(),
                d.getType(),
                DealDetailDTO.buildDtoList(d.getDetails())
        );
        return dto;
    }
}
