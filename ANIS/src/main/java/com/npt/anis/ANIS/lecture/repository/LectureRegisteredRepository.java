package com.npt.anis.ANIS.lecture.repository;

import com.npt.anis.ANIS.lecture.domain.entity.LectureRegistered;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureRegisteredRepository extends JpaRepository<LectureRegistered,Long> {
    @Query("SELECT l as lecture, lr.selected as selected, lr.lecYear as lecYear, lr.lecSemester as lecSemester " +
        "FROM Lecture l " +
        "LEFT JOIN LectureRegistered lr " +
        "ON lr.lecID = l.lecID " +
        "WHERE lr.studentID = :studentID")
    List<Tuple> getByLectureRegisteredWithLecture(@Param("studentID") String studentID);


    List<LectureRegistered> findByStudentID(String studentID);

    boolean existsByStudentID(String studentID);

}
