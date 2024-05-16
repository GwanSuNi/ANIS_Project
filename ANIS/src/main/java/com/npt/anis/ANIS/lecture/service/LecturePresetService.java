package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;

import java.util.List;

public interface LecturePresetService {
    public LecturePresetDto createLecturePreset(LecturePresetDto lecturePresetDto);
    public List<LecturePresetDto> availableLecturePreset();
    public long getLecturePresetIndex(String lpName);
}