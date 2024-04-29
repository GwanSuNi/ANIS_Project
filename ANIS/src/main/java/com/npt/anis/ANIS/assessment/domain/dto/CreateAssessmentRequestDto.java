package com.npt.anis.ANIS.assessment.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAssessmentRequestDto {
    @NotNull
    private Long lecId;
    @NotBlank
    private String diagnosisArea;
    @NotBlank
    private String diagnosisQuestion;
}
