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
public class LectureRegistered {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lrIndex;
    // Lecture FK
    private Long lecID;
    // Member FK
    private String studentID;

    public LectureRegistered(long lecID , String studentID){
        this.lecID = lecID;
        this.studentID = studentID;
    }
}
