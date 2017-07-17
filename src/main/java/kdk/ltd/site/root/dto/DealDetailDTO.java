package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.DealDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class DealDetailDTO extends DetailDTO {

    private String storage;

    public DealDetailDTO(long id, String product, int qnt, BigDecimal sum, String storage) {
        super(id, product, qnt, sum);
        this.storage = storage;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public static List<DealDetailDTO> buildDtoList(List<DealDetail> details) {
        List<DealDetailDTO> dtos = new ArrayList<>(details.size());
        for (DealDetail d: details) {
            dtos.add( build(d) );
        }
        return dtos;
    }

    public static DealDetailDTO build(DealDetail d) {
        DealDetailDTO dto = new DealDetailDTO(
                d.getId(),
                d.getProduct().getName(),
                d.getQuantity(),
                d.getSum(),
                d.getStorage().getName()
        );
        return dto;
    }
}
