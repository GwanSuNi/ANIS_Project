package com.npt.anis.ANIS.assessment.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAssessmentRequestDto {
    private Long lecId;
    private String diagnosisArea;
    private String diagnosisQuestion;
}
