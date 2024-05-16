package com.npt.anis.ANIS.lecture.domain.mapper;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface LectureMapper {
    LectureDto toDto(Lecture lecture);

    Lecture toEntity(LectureDto lectureDto);

    List<Lecture> toEntities(List<LectureDto> lectureDtos);
    List<LectureDto> toDtos(List<Lecture> lectures);
    @Mapping(target = "lecName", source = "lecName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lecCredit", source = "lecCredit", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lecYear", source = "lecYear", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lecSemester", source = "lecSemester", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lecProfessor", source = "lecProfessor", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lecDay", source = "lecDay", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lecTimeStart", source = "lecTimeStart", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lecTimeEnd", source = "lecTimeEnd", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lectureRoom", source = "lectureRoom", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(LectureDto dto, @MappingTarget Lecture entity);
}
