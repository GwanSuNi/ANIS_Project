package com.npt.anis.ANIS.assessment.service;

import com.npt.anis.ANIS.assessment.domain.dto.request.CreateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.request.UpdateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.CreateAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.GetAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.UpdateAssessmentResponseDto;

import java.util.List;

public interface AssessmentService {
    CreateAssessmentResponseDto createAssessment(CreateAssessmentRequestDto requestDto);

    GetAssessmentResponseDto findById(Long id);

    List<GetAssessmentResponseDto> findAll();

    UpdateAssessmentResponseDto update(Long id, UpdateAssessmentRequestDto requestDto);

    void deleteById(Long id);
}
