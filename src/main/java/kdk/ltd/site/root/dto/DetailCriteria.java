package kdk.ltd.site.root.dto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


public class DetailCriteria {

    private List<Long> productIdList = new LinkedList<>();

    private List<Long> storageIdList = new LinkedList<>();

    private BigDecimal sumMin;

    private BigDecimal sumMax;

    private Integer quantityMin;

    private Integer quantityMax;

    public List<Long> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Long> productIdList) {
        this.productIdList = productIdList;
    }

    public List<Long> getStorageIdList() {
        return storageIdList;
    }

    public void setStorageIdList(List<Long> storageIdList) {
        this.storageIdList = storageIdList;
    }

    public BigDecimal getSumMin() {
        return sumMin;
    }

    public void setSumMin(BigDecimal sumMin) {
        this.sumMin = sumMin;
    }

    public BigDecimal getSumMax() {
        return sumMax;
    }

    public void setSumMax(BigDecimal sumMax) {
        this.sumMax = sumMax;
    }

    public Integer getQuantityMin() {
        return quantityMin;
    }

    public void setQuantityMin(Integer quantityMin) {
        this.quantityMin = quantityMin;
    }

    public Integer getQuantityMax() {
        return quantityMax;
    }

    public void setQuantityMax(Integer quantityMax) {
        this.quantityMax = quantityMax;
    }


}
