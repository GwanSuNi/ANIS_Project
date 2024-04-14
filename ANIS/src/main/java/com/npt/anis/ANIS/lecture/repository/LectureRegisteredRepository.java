package com.npt.anis.ANIS.lecture.repository;

import com.npt.anis.ANIS.lecture.domain.entity.LectureRegistered;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureRegisteredRepository extends JpaRepository<LectureRegistered,Long> {

    @Query("SELECT l as lecture , lr as lectureRegistered " +
            "from Lecture l " +
            "left join LectureRegistered lr " +
            "on lr.lecID = l.lecID " +
            "WHERE lr.studentID = :studentID ")
    List<Tuple> getByLectureRegisteredWithLecture(@Param("studentID") String studentID);

    List<LectureRegistered> findByStudentID(String studentID);
}
