package com.npt.anis.ANIS.assessment.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CreateAssessmentItemRequestDto {
    @NotBlank(message = "Assessment area item cannot be empty.")
    private String assmtArea;

    @NotBlank(message = "Assessment question item cannot be empty.")
    private String assmtQuestion;
}
