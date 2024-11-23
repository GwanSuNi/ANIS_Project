package com.npt.anis.ANIS.assessment.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Builder
public class UpdateAssessmentRequestDto {
    private String assmtName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<UpdateAssessmentItemRequestDto> assmtItems;
}
