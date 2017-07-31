package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.DealDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class DealDetailDto extends DetailDto {

    private String storage;

    public DealDetailDto(long id, Integer version, String product, int qnt, BigDecimal sum, String storage) {
        super(id, version, product, qnt, sum);
        this.storage = storage;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public static List<DealDetailDto> buildDtoList(List<DealDetail> details) {
        List<DealDetailDto> dtos = new ArrayList<>(details.size());
        for (DealDetail d: details) {
            dtos.add( build(d) );
        }
        return dtos;
    }

    public static DealDetailDto build(DealDetail d) {
        return new DealDetailDto(
                d.getId(),
                d.getVersion(),
                d.getProduct().getName(),
                d.getQuantity(),
                d.getSum(),
                d.getStorage().getName()
        );
    }
}
