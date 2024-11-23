package com.npt.anis.ANIS.assessment.domain.mapper;

import com.npt.anis.ANIS.assessment.domain.dto.request.CreateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.request.UpdateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.CreateAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.GetAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.UpdateAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.entity.Assessment;
import com.npt.anis.ANIS.assessment.domain.entity.AssessmentItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AssessmentItemMapper.class})
public interface AssessmentMapper {
    Assessment toCreateAssessmentEntity(CreateAssessmentRequestDto dto);

    CreateAssessmentResponseDto toCreateAssessmentResponseDto(Assessment assmt, List<AssessmentItem> assmtItems);

    GetAssessmentResponseDto toGetAssessmentResponseDto(Assessment assmt, List<AssessmentItem> assmtItems);

    // @MappingTarget으로 대상 엔티티를 지정하고 UpdateAssessmentRequestDto 필드를 Assessment에 매핑함.
    // 이때 null 값은 매핑되지 않음.
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateFromDto(@MappingTarget Assessment entity, UpdateAssessmentRequestDto dto);

    UpdateAssessmentResponseDto toUpdateAssessmentResponseDto(Assessment assmt, List<AssessmentItem> assmtItems);
}
