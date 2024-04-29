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
    // lectureIndex FK
    private Long lectureID;
    private String presetName;

    public LecturePreset(long lectureID,String presetName){
        this.lectureID = lectureID;
        this.presetName = presetName;
    }
}
