package com.npt.anis.ANIS.lecture.domain.mapper;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.dto.LectureRegisteredDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import com.npt.anis.ANIS.lecture.domain.entity.LectureRegistered;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LectureRegisteredMapper {
    LectureRegisteredDto toDto(LectureRegistered lectureRegistered);
    LectureRegistered toEntity(LectureRegisteredDto lectureRegisteredDto);
    List<LectureRegistered> toEntities(List<LectureRegisteredDto> lectureRegisteredDtoList);
    List<LectureRegisteredDto> toDtos(List<LectureRegistered> lectureRegisteredList);
}
