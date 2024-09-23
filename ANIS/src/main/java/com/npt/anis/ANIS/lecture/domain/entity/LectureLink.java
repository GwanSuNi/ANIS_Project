package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class LectureLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkIndex;

    // LecturePreset의 기본키
    private Long lpIndex;

    // Lecture의 기본키
    private Long lecID;

}
