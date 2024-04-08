package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    Lecture createLecture(LectureDto lectureDto);
    Lecture updateLecture(long lecId,LectureDto updateLectureDto);
    boolean deleteLecture(long lecId);
    List<Lecture> showLectureList();
    List<Lecture> showNoPresetLectureList();
    List<Lecture> findLecturePreset(long lpIndex);


}
