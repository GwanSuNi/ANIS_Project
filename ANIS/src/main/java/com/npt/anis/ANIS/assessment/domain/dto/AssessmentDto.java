package com.npt.anis.ANIS.assessment.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AssessmentDto {
    private Long asIndex;
    private Long lecId;
    private String diagnosisArea;
    private String diagnosisQuestion;
}
