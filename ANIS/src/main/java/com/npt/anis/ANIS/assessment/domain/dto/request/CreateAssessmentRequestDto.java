package com.npt.anis.ANIS.assessment.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Builder
public class CreateAssessmentRequestDto {
    @NotNull(message = "Lecture ID is required.")
    private Long lecID;

    @NotBlank(message = "Assessment name is required.")
    private String assmtName;

    @NotNull(message = "Assessment start date is required.")
    private LocalDateTime startDate;

    @NotNull(message = "Assessment end date is required.")
    private LocalDateTime endDate;

    @Size(min = 1, message = "Assessment item list must contain at least one item.")
    private List<CreateAssessmentItemRequestDto> assmtItems;
}
