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
public class LecturePreset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lpIndex;
    private String presetName;
    private int lecYear;
    private int lecSemester;
    @PrePersist
    public void prePersist() {
        this.lecSemester = DateUtils.getCurrentSemester();
        this.lecYear = DateUtils.getCurrentYear();
    }
    public LecturePreset(String presetName, int lecYear, int lecSemester){
        this.presetName = presetName;
        this.lecYear = lecYear;
        this.lecSemester = lecSemester;
    }
}
