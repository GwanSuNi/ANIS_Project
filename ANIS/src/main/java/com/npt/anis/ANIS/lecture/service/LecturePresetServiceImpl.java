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
    public LecturePreset createLecturePreset(long lecIndex,String presetName){
        LecturePreset lecturePreset = new LecturePreset(lecIndex,presetName);
        return lecturePresetRepository.save(lecturePreset);
    }
    @Override
    public List<Lecture> findLectureList(long lpIndex) {
        return null;
    }

}
