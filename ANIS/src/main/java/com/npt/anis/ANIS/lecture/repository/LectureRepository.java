package com.npt.anis.ANIS.lecture.repository;

import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture,Long> {
    @Query("SELECT l as lecture , lp as lecturePreset " +
            "from Lecture l " +
            "left join LecturePreset lp " +
            "on lp.lpIndex = l.lpIndex " +
            "WHERE lp.lpIndex = :lpIndex ")
    List<Tuple> getByLecturePresetWithLecture(@Param("lpIndex") long lpIndex);

    List<Lecture> findAllBylpIndex(long lpIndex);

//    쿼리문으로 현재 날짜를 갖고오려고 시도를 했지만
//    int를 timestamp 로 바꾸라고 해서 굳이 데이터타입을 바꾸면서까지 하고싶지않음
//    그래서 service 에서 검증해서 값을 반환함
//    @Query(value = "SELECT * FROM lecture WHERE YEAR(lec_year) = YEAR(CURDATE())", nativeQuery = true)

}
