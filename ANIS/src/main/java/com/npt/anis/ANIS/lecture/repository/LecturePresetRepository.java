package com.npt.anis.ANIS.lecture.repository;

import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LecturePresetRepository extends JpaRepository<LecturePreset,Long> {
    List<LecturePreset> findByPresetName(String presetName);
}
