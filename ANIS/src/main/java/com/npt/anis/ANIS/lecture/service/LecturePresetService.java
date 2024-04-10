package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;

import java.util.List;

public interface LecturePresetService {
    LecturePreset createLecturePreset(long lecIndex,String presetName);
    List<Lecture> findLectureList(long lpIndex);

}
