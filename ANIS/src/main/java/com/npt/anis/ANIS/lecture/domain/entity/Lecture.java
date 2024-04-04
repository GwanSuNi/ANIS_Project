package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.Entity;
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
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {
    @Id
    private Long lecID;
    private String lecName;
    private int lecCredit;
    private int lecYear;
    private int lecSemester;
    private String lecProfessor;
    private String lecDay;
    private LocalDateTime lecTimeStart;
    private LocalDateTime lecTimeEnd;

}
