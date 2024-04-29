package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LecturePresetMapper;
import com.npt.anis.ANIS.lecture.exception.NotFoundLectureException;
import com.npt.anis.ANIS.lecture.exception.NotFoundLecturePresetException;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LecturePresetServiceImpl implements LecturePresetService {
    private final LecturePresetRepository lecturePresetRepository;
    private final LecturePresetMapper lecturePresetMapper;
    private final LectureRepository lectureRepository;
    public LecturePresetDto createLecturePreset(LecturePresetDto lecturePresetDto){
        LecturePreset lecturePreset = new LecturePreset(lecturePresetDto.getLectureID(),lecturePresetDto.getPresetName());
        return lecturePresetMapper.toDto(lecturePresetRepository.save(lecturePreset));
    }
}
