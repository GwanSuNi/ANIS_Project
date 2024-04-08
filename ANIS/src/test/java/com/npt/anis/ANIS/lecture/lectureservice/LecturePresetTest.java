package com.npt.anis.ANIS.lecture.lectureservice;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LecturePresetMapper;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import com.npt.anis.ANIS.lecture.service.LecturePresetService;
import com.npt.anis.ANIS.lecture.service.LecturePresetServiceImpl;
import com.npt.anis.ANIS.lecture.service.LectureService;
import com.npt.anis.ANIS.lecture.service.LectureServiceImpl;
import org.aspectj.lang.annotation.RequiredTypes;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.hibernate.query.sqm.tree.SqmNode.log;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(MockitoExtension.class) // Mockito를 사용하여 Mock 객체를 주입할 경우
//@RunWith(SpringRunner.class)
@SpringBootTest
public class LecturePresetTest {
    @Autowired
    private LectureRepository lectureRepository;
    private LectureService lectureService;
    @Autowired
    private LecturePresetRepository lecturePresetRepository;
    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private LecturePresetMapper lecturePresetMapper;
    private LecturePresetService lecturePresetService;
    private LectureDto lectureDto1;
    private LectureDto lectureDto2;
    private LectureDto lectureDto3;

    @BeforeEach
    public void dependencySetting(){
        lectureService = new LectureServiceImpl(lectureRepository, lectureMapper);
        lecturePresetService = new LecturePresetServiceImpl(lecturePresetRepository,lecturePresetMapper,lectureRepository);
        lectureDto1 = new LectureDto(1,1,"name",100,2024,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
        lectureDto2 = new LectureDto(2,1,"name",100,2024,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
        lectureDto3 = new LectureDto(3,1,"name",100,2024,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
    }
    @Test
    @DisplayName("preset 이 갖고있는 LectureList 갖고오기")
    public void presetRead(){
        Lecture lecture1 = lectureService.createLecture(lectureDto1);
        Lecture lecture2 = lectureService.createLecture(lectureDto2);
        Lecture lecture3 = lectureService.createLecture(lectureDto3);
        LecturePreset lecturePreset = new LecturePreset();
        List<Long> lecture_list = new ArrayList<>();
        lecture_list.add(lecture1.getLecID());
        lecture_list.add(lecture2.getLecID());
        lecture_list.add(lecture3.getLecID());
        lecturePreset.setPresetName("A");
        lecturePreset.setLectureList(lecture_list);
        lecturePresetRepository.save(lecturePreset);
        assertEquals(3,lecturePresetService.findLectureList(lecturePreset.getLpIndex()).size());
        assertEquals(lecturePreset.getLectureList().get(0),lecture1.getLecID());
        assertEquals(lecturePreset.getLectureList().get(1),lecture2.getLecID());
        assertEquals(lecturePreset.getLectureList().get(2),lecture3.getLecID());
    }

    @Test
    @DisplayName("lecture 리스트 값 넣어보기")
    public void createList(){
        Lecture lecture1 = lectureService.createLecture(lectureDto1);
        Lecture lecture2 = lectureService.createLecture(lectureDto2);
        Lecture lecture3 = lectureService.createLecture(lectureDto3);
        List<Long> lecture_list = new ArrayList<>();
        LecturePreset lecturePreset = new LecturePreset();
        lecturePreset.setPresetName("A");
        lecturePreset.setLectureList(lecture_list);
        lecturePresetRepository.save(lecturePreset);
        lecturePreset.getLectureList().add(lecture1.getLpIndex());
        lecturePreset.getLectureList().add(lecture2.getLpIndex());
        lecturePreset.getLectureList().add(lecture3.getLpIndex());
        lecturePresetService.saveLecturePreset(lecturePreset);
        assertEquals(3, lecturePreset.getLectureList().size()); // lecture_list에 3개의 요소가 저장되었는지 확인
    }
}
