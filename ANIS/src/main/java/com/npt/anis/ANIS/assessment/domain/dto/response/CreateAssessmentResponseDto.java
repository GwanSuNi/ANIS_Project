package com.npt.anis.ANIS.assessment.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Builder
public class CreateAssessmentResponseDto {
    private Long lecID;
    private String assmtName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<CreateAssessmentItemResponseDto> assmtItems;
}
