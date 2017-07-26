package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


public class DealCriteria {

    private List<GenericDeal.Type> dealTypes = new LinkedList<>();

    private List<Long> partnerIdList = new LinkedList<>();

    private LocalDateTime from;

    private LocalDateTime to;

    public List<GenericDeal.Type> getDealTypes() {
        return dealTypes;
    }

    public void setDealTypes(List<GenericDeal.Type> dealTypes) {
        this.dealTypes = dealTypes;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public List<Long> getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerIdList(List<Long> partnerIdList) {
        this.partnerIdList = partnerIdList;
    }
}
