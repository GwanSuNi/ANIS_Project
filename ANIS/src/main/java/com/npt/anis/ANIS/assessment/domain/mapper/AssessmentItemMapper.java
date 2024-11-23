package com.npt.anis.ANIS.assessment.domain.mapper;

import com.npt.anis.ANIS.assessment.domain.dto.request.CreateAssessmentItemRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.request.UpdateAssessmentItemRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.request.UpdateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.CreateAssessmentItemResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.GetAssessmentItemResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.UpdateAssessmentItemResponseDto;
import com.npt.anis.ANIS.assessment.domain.entity.Assessment;
import com.npt.anis.ANIS.assessment.domain.entity.AssessmentItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssessmentItemMapper {
    @Mapping(target = "assmtId", expression = "java(assmtId)")
    AssessmentItem toAssessmentItemEntity(@Context Long assmtId, CreateAssessmentItemRequestDto dto);

    List<AssessmentItem> toAssessmentItemEntities(@Context Long assmtId, List<CreateAssessmentItemRequestDto> dtos);

    CreateAssessmentItemResponseDto toCreateAssessmentItemResponseDto(AssessmentItem entity);

    List<CreateAssessmentItemResponseDto> toCreateAssessmentItemResponseDtos(List<AssessmentItem> entities);

    GetAssessmentItemResponseDto toGetAssessmentItemResponseDto(AssessmentItem entity);

    List<GetAssessmentItemResponseDto> toGetAssessmentItemResponseDtos(List<AssessmentItem> entities);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateFromDto(@MappingTarget AssessmentItem entity, UpdateAssessmentItemRequestDto dto);

    UpdateAssessmentItemResponseDto toUpdateAssessmentItemResponseDto(AssessmentItem entity);

    List<UpdateAssessmentItemResponseDto> toUpdateAssessmentItemResponseDtos(List<AssessmentItem> entities);
}
