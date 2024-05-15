package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public LecturePreset(String presetName, int lecYear, int lecSemester){
        this.presetName = presetName;
        this.lecYear = lecYear;
        this.lecSemester = lecSemester;
    }
}
