package com.npt.anis.ANIS.lecture.domain.dto;

import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto {
        private long lecID;
        private long lpIndex;
        private String lecName;
        private int lecCredit;
        private int lecYear;
        private int lecSemester;
        private String lecProfessor;
        private String lecDay;
        private LocalDateTime lecTimeStart;
        private LocalDateTime lecTimeEnd;
}
