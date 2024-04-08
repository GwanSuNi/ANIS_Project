package com.npt.anis.ANIS.lecture.service;

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
    /***
     * LecturePreset은 많이 만들필요도 없고 , 컬럼 갯수도 적기때문에 매퍼를 사용하지 않았다
     * @return
     */
    @Override
    public LecturePreset createLecturePreset(String presetName){
        List<Long> lecture_list = new ArrayList<>();
        LecturePreset lecturePreset = new LecturePreset(presetName,lecture_list);
        return lecturePresetRepository.save(lecturePreset);
    }

    /***
     * @param lectureIndexList 최신화할 lecture index list
     * @return
     */
    @Override
    public LecturePreset updateLecturePreset(List<Long> lectureIndexList){
        LecturePreset lecturePreset = new LecturePreset();
        return lecturePreset;
    }

    public void saveLecturePreset(LecturePreset lecturePreset) {
        lecturePresetRepository.save(lecturePreset);
    }
    public List<Lecture> findLectureList(long lpIndex){
        List<Lecture> lectureList = new ArrayList<>();
        LecturePreset lecturePreset = lecturePresetRepository.findById(lpIndex)
                .orElseThrow(()-> new NotFoundLecturePresetException("No LecturePreset"));
        for (Long lectureIndex: lecturePreset.getLectureList()) {
            Optional<Lecture> optionalLecture = lectureRepository.findById(lectureIndex);
            if (optionalLecture.isPresent()) {
                lectureList.add(optionalLecture.get());
            } else {
                // Lecture를 찾지 못한 경우에 대한 예외 처리
                throw new NotFoundLectureException("Lecture not found for index: " + lectureIndex);
            }
        }
        return lectureList;
    }
}
