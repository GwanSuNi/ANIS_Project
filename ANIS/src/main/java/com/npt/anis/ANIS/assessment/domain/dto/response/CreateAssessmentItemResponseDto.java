package com.npt.anis.ANIS.assessment.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CreateAssessmentItemResponseDto {
    private String assmtArea;
    private String assmtQuestion;
}
