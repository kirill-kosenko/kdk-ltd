package kdk.ltd.site.root.repositories;


import kdk.ltd.site.root.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PartnerRepository extends JpaRepository<Partner, Long> {
    List<Partner> findByNameLikeIgnoreCase(String name);
    Partner findByName(String name);
    List<Partner> findAll();
    @Modifying
    @Query("update Partner p set p.name = ?1 where p.id = ?2")
    void update(String name, Long id);
}
