package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lecID;
    /* 어드민에서 lecturePreset을 만들기위해서 lecturePreset에 등록되지않은
    lecture들의 목록들을 보여주기위해서 lpIndex 가 필요하다고 생각함
    */
    private Long lpIndex;
    private String lecName;
    // hour
    private int lecCredit;
    private int lecYear;
    private int lecSemester;
    private String lecProfessor;
    private String lecDay;
    private int lecGrade;
    private LocalTime lecTimeStart;
    private LocalTime lecTimeEnd;
    private String lectureRoom;
    @PrePersist
    public void prePersist() {
        this.lecSemester = getCurrentSemester();
        this.lecYear = getCurrentYear();
    }
    private int getCurrentSemester() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        //
        if (month >= 3 && month <= 7) {
            return 1;
        } else {
            return 2;
        }
    }
    private int getCurrentYear() {
        LocalDate now = LocalDate.now();
        return now.getYear();
    }

}


