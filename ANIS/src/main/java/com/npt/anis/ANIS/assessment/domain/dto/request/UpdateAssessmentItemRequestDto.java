package com.npt.anis.ANIS.assessment.domain.dto.request;

import lombok.*;

@Getter
@ToString
@Builder
public class UpdateAssessmentItemRequestDto {
    private Long itemId;
    private String assmtArea;
    private String assmtQuestion;
    private boolean isDeleted;
}
