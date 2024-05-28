package com.npt.anis.ANIS.IdPhoto.repository;

import com.npt.anis.ANIS.IdPhoto.entity.IdPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IdPhotoRepository extends JpaRepository<IdPhoto, Long> {
    @Query("SELECT i FROM IdPhoto i WHERE i.name = :name")
    IdPhoto findByName(@Param("name") String name);

    @Modifying
    @Query("update IdPhoto i set i.path = '' where i.name = :name")
    void deleteByName(@Param("name") String name);
}
