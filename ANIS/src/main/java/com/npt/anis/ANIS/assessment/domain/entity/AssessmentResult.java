package com.npt.anis.ANIS.assessment.domain.entity;

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
public class AssessmentResult {
    @Id
    private Long arIndex;
    // assessment 외래키
    private Long asIndex;
     // student 외래키
    private String studentId;
    private int arScore;
}

