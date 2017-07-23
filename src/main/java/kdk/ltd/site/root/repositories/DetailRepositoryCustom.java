package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.DealDetail;

import java.util.List;

public interface DetailRepositoryCustom {
    void saveBatch(List<DealDetail> list);
}
