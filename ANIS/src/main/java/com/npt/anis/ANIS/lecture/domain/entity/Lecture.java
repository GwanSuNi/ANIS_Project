package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lecID;
    /* 어드민에서 lecturePreset을 만들기위해서 lecturePreset에 등록되지않은
    lecture들의 목록들을 보여주기위해서 lpIndex 가 필요하다고 생각함
    */
    private long lpIndex;
    private String lecName;
    private int lecCredit;
    private int lecYear;
    private int lecSemester;
    private String lecProfessor;
    // 1=월요일, 2=화요일, 3=수요일 ...
    private int lecDay;
    private int lecGrade;
    private LocalDateTime lecTimeStart;
    private LocalDateTime lecTimeEnd;

}
