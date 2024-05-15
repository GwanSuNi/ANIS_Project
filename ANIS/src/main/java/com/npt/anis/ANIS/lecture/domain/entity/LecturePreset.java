package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturePreset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lpIndex;
    private String presetName;
    private int lecYear;
    private int lecSemester;
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

    public LecturePreset(String presetName, int lecYear, int lecSemester){
        this.presetName = presetName;
        this.lecYear = lecYear;
        this.lecSemester = lecSemester;
    }
}
