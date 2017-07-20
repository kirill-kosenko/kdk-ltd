package kdk.ltd.site.root.repositories;

import kdk.ltd.site.root.entities.Deal;

import java.util.List;

public interface DealRepositoryCustom {
    void saveBatch(List<Deal> deals);
}
