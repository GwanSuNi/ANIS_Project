package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    // 강의 생성하기
    LectureDto createLecture(LectureDto lectureDto);
    // 강의 업데이트하기
    LectureDto updateLecture(long lecId,LectureDto updateLectureDto);
    // 강의 삭제하기
    boolean deleteLecture(long lecId);
    // 모든 강의 보기
    List<LectureDto> showLectureList();
    // preset에 등록되지않은 강의 보기
    List<LectureDto> showNoPresetLectureList();
    // LecturePreset 찾기
    List<LectureDto> findLecturePreset(long lpIndex);
    // 프리셋이 갖고있는 LectureList 보기
    List<LectureDto> getLectureListByPreset(long lpIndex);
    // 프리셋이 갖고있는 LectureList 수정하기
    List<LectureDto> updateLecturePresetOfLectureList(Long lpIndex,List<LectureDto> newLectureList);
    // 현재 수강 가능한 수강신청 리스트를 갖고오는 메서드(현재년도,학기기준)
    List<LectureDto> showAvailableLectureList();
}
