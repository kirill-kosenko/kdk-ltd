package kdk.ltd.site.root.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import kdk.ltd.site.root.entities.Storage;

import java.util.List;


public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query(value = "select s.name from Storage s")
    List<String> findAllNames();

    Storage findByName(String name);

    @Modifying
    @Query(value = "update Storage s set s.name = ?2 where s.id = ?1")
    void updateName(Long id, String name);
}
