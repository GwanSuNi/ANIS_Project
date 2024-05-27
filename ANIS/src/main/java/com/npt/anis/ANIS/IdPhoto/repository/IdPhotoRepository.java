package com.npt.anis.ANIS.IdPhoto.repository;

import com.npt.anis.ANIS.IdPhoto.entity.IdPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IdPhotoRepository extends JpaRepository<IdPhoto, Long> {
    @Query("SELECT i FROM IdPhoto i WHERE i.name = :name")
    IdPhoto findByName(@Param("name") String name);
}
