package kdk.ltd.site.root.dto;


public class DealSearchCriteria {

    private DealCriteria dealCriteria;

    private DetailCriteria detailCriteria;

    public DealCriteria getDealCriteria() {
        return dealCriteria;
    }

    public void setDealCriteria(DealCriteria dealCriteria) {
        this.dealCriteria = dealCriteria;
    }

    public DetailCriteria getDetailCriteria() {
        return detailCriteria;
    }

    public void setDetailCriteria(DetailCriteria detailCriteria) {
        this.detailCriteria = detailCriteria;
    }
}
