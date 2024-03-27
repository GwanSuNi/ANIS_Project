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
    // private Long asIndex; assessment 외래키
    // private Long studentId; student 외래키
    // erd에 대한 얘기가 끝나지 않아서 비워둠
    private int arScore;
}

