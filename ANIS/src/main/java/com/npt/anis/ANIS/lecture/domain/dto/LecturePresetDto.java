package com.npt.anis.ANIS.lecture.domain.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturePresetDto {
    private Long lpIndex;
    private String presetName;
    private int lecYear;
    private int lecSemester;
}
