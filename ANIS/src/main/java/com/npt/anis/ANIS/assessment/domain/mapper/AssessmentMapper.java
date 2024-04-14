package com.npt.anis.ANIS.assessment.domain.mapper;

import com.npt.anis.ANIS.assessment.domain.dto.AssessmentDto;
import com.npt.anis.ANIS.assessment.domain.dto.CreateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.UpdateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.entity.Assessment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {
    AssessmentDto toDto(Assessment entity);

    Assessment toEntity(AssessmentDto dto);

    List<AssessmentDto> toDtos(List<Assessment> assessments);

    AssessmentDto toDto(CreateAssessmentRequestDto dto);

    AssessmentDto toDto(UpdateAssessmentRequestDto dto);

    // @MappingTarget으로 대상 엔티티를 지정하고 AssessmentDto의 필드를 Assessment에 매핑함. 이때 null 값은 매핑되지 않음
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS) // null 값은 매핑되지 않게 해줌
    void updateFromDto(@MappingTarget Assessment entity, AssessmentDto dto);
}
