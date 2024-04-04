package com.npt.anis.ANIS.assessment.domain.entity;

import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import jakarta.persistence.Entity;
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
public class Assessment {
    @Id
    private Long asIndex;
    // Lecture에 대한 외래키
    private Long lecId;
    private String diagnosisArea;
    private String diagnosisQuestion;


}
