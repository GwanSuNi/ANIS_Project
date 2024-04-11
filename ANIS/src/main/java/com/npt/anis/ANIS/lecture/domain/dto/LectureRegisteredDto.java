package com.npt.anis.ANIS.lecture.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureRegisteredDto {
    private Long lrIndex;
    // Lecture FK
    private Long lecID;
    // Member FK
    private String studentID;

    public LectureRegisteredDto(Long lecID, String studentID) {
        this.lecID = lecID;
        this.studentID = studentID;
    }
}
