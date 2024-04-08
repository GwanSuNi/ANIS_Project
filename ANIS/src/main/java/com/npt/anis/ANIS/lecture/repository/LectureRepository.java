package com.npt.anis.ANIS.lecture.repository;

import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture,Long> {
//    @Query("SELECT l as lecutre, lp as lecturePreset from LecturePreset lp left join Lecture l on lp.lecID = l.lecID")
//    List<Tuple> getByLecturePresetWithLecture(Long id);
    List<Lecture> findAllBylpIndex(long lpIndex);
}
