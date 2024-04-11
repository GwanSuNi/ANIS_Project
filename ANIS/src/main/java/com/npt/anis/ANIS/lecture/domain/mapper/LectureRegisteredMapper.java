package com.npt.anis.ANIS.lecture.domain.mapper;

import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.dto.LectureRegisteredDto;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import com.npt.anis.ANIS.lecture.domain.entity.LectureRegistered;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LectureRegisteredMapper {
    LectureRegisteredDto toDto(LectureRegistered lectureRegistered);
    LectureRegistered toEntity(LectureRegisteredDto lectureRegisteredDto);

}
