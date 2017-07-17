package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class DealCriteria {

    private List<GenericDeal.Type> dealTypes = new LinkedList<>();

    private List<Long> partnerIdList = new LinkedList<>();

    private LocalDate from;

    private LocalDate to;

    public List<GenericDeal.Type> getDealTypes() {
        return dealTypes;
    }

    public void setDealTypes(List<GenericDeal.Type> dealTypes) {
        this.dealTypes = dealTypes;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public List<Long> getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerIdList(List<Long> partnerIdList) {
        this.partnerIdList = partnerIdList;
    }
}
