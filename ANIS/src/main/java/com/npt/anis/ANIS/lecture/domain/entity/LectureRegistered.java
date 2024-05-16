package com.npt.anis.ANIS.lecture.domain.entity;

import com.npt.anis.ANIS.global.util.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureRegistered {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lrIndex;
    // Lecture FK
    private Long lecID;
    // Member FK
    private String studentID;
    private int lecYear;
    private int lecSemester;
    // 현재 수강중인지 아닌지에 대한 판단여부 컬럼
    private boolean selected;
    @PrePersist
    public void prePersist() {
        this.lecSemester = DateUtils.getCurrentSemester();
        this.lecYear = DateUtils.getCurrentYear();
    }
    public LectureRegistered(long lecID , String studentID){
        this.lecID = lecID;
        this.studentID = studentID;
    }

}
