package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;

import java.util.List;

public interface LecturePresetService {
    LecturePreset createLecturePreset(String presetName);
    LecturePreset updateLecturePreset(List<Long> lectureList);
    void saveLecturePreset(LecturePreset lecturePreset);
    List<Lecture> findLectureList(long lpIndex);

}
