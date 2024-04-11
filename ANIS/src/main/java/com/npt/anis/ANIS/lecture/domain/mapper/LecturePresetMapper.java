package com.npt.anis.ANIS.lecture.domain.mapper;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LecturePresetMapper {
    LecturePresetDto toDto(LecturePreset lecturePreset);
    LecturePreset toEntity(LecturePresetDto lecturePresetDto);
    List<LecturePresetDto> toDtos(List<LecturePreset> lecturePresetList);
    List<LecturePreset> toEntities(List<LecturePresetDto> lecturePresetDtos);
}
