package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturePreset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lpIndex;
    // lectureIndex FK
    private long lectureID;
    private String presetName;

    public LecturePreset(long lectureID,String presetName){
        this.lectureID = lectureID;
        this.presetName = presetName;
    }
}
