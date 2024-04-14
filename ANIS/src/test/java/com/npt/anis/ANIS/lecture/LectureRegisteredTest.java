package com.npt.anis.ANIS.lecture;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LectureRegistered;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LecturePresetMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureRegisteredMapper;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRegisteredRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import com.npt.anis.ANIS.lecture.service.*;
import org.hibernate.annotations.NaturalId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LectureRegisteredTest {
    @Autowired
    private LectureRepository lectureRepository;
    private LectureService lectureService;
    @Autowired
    private LecturePresetRepository lecturePresetRepository;

    @Autowired
    private LectureRegisteredRepository lectureRegisteredRepository;
    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private LecturePresetMapper lecturePresetMapper;
    private LectureRegisteredService lectureRegisteredService;
    private LecturePresetService lecturePresetService;
    private LectureDto lectureDto1;
    private LectureDto lectureDto2;
    private LectureDto lectureDto3;
    private LecturePresetDto lecturePresetDto1;
    private LecturePresetDto lecturePresetDto2;
    private LecturePresetDto lecturePresetDto3;
    private LectureRegistered lectureRegistered1;
    private LectureRegistered lectureRegistered2;
    private LectureRegistered lectureRegistered3;
    @Autowired
    private  LectureRegisteredMapper lectureRegisteredMapper;



    @BeforeEach
    public void dependencySetting(){
        lectureService = new LectureServiceImpl(lectureRepository, lectureMapper,lecturePresetRepository);
        lecturePresetService = new LecturePresetServiceImpl(lecturePresetRepository,lecturePresetMapper,lectureRepository);
        lectureRegisteredService = new LectureRegisteredServiceImpl(lectureRegisteredRepository,lectureMapper,lectureRegisteredMapper);
        lecturePresetDto1 = new LecturePresetDto(1L,null,"a");
        lecturePresetDto2 = new LecturePresetDto(2L,null,"b");
        lecturePresetDto3 = new LecturePresetDto(3L,null,"c");
        lectureDto1 = new LectureDto(1L,lecturePresetDto1.getLpIndex(),"name",5,2024,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        lectureDto2 = new LectureDto(2L,lecturePresetDto2.getLpIndex(),"name",5,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        lectureDto3 = new LectureDto(3L,lecturePresetDto3.getLpIndex(),"name",5,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
    }
    @Test
    @DisplayName("수강신청테스트")
    public void lectureRegistered(){
        // given
        List<LectureDto> lectureDtoList = new ArrayList<>();
        lectureService.createLecture(lectureDto1);
        lectureService.createLecture(lectureDto2);
        lectureService.createLecture(lectureDto3);
        lectureDtoList.add(lectureDto1);
        lectureDtoList.add(lectureDto2);
        lectureDtoList.add(lectureDto3);
        // when
        boolean flag = lectureRegisteredService.lectureRegistered("상현",lectureDtoList);
        // then
        assertEquals(true,flag);

    }

    /***
     * 최소 이수학점과 최대 이수학점이어야만 수강신청이 가능하게 만들어서
     * 테스트에서는 학점이 10점이라 수강신청이 안된다
     */
    @Test
    @DisplayName("수강신청실패테스트")
    public void notLectureRegistered(){
        // given
        List<LectureDto> lectureDtoList = new ArrayList<>();
        lectureService.createLecture(lectureDto1);
        lectureService.createLecture(lectureDto2);
        lectureDtoList.add(lectureDto1);
        lectureDtoList.add(lectureDto2);
        // when
        boolean flag = lectureRegisteredService.lectureRegistered("상현",lectureDtoList);
        // then
        assertEquals(false,flag);
    }

    /***
     * 단일테스트로 진행하면 성공하는데 한꺼번에 테스트 실행하면 실패함 이유를 모르겠음
     */
    @Test
    @DisplayName("친구 수강신청 받아오기 테스트")
    public void friendOfLectureTest(){
        // given
        List<LectureDto> lectureDtoList = new ArrayList<>();
        lectureService.createLecture(lectureDto1);
        lectureService.createLecture(lectureDto2);
        lectureService.createLecture(lectureDto3);
        lectureDtoList.add(lectureDto1);
        lectureDtoList.add(lectureDto2);
        lectureDtoList.add(lectureDto3);
        boolean flag = lectureRegisteredService.lectureRegistered("상현",lectureDtoList);
        // when
        List<LectureDto> lectureDtos = lectureRegisteredService.studentLectureList("상현");
        // then
        assertEquals(true,flag);
        assertEquals(3,lectureDtos.size());
    }
    @Test
    @DisplayName("친구 수강신청 따라하기 메서드")
    public void lectureRegisteredWithFriend(){
        // given
        List<LectureDto> lectureDtoList = new ArrayList<>();
        lectureService.createLecture(lectureDto1);
        lectureService.createLecture(lectureDto2);
        lectureService.createLecture(lectureDto3);
        lectureDtoList.add(lectureDto1);
        lectureDtoList.add(lectureDto2);
        lectureDtoList.add(lectureDto3);
        boolean flag = lectureRegisteredService.lectureRegistered("상현",lectureDtoList);
        // when
        List<LectureDto> lectureDtos = lectureRegisteredService.studentLectureList("상현");
        assertEquals(3,lectureDtos.size());
        lectureRegisteredService.lectureRegisteredWithFriend("관형","상현");
        List<LectureRegistered> lectureRegisteredList = lectureRegisteredRepository.findAll();
        // then
        assertEquals(6,lectureRegisteredList.size());
        assertEquals("관형",lectureRegisteredList.get(3).getStudentID());
        assertEquals("관형",lectureRegisteredList.get(4).getStudentID());
        assertEquals("관형",lectureRegisteredList.get(5).getStudentID());
    }
    @Test
    @DisplayName("친구와 수강신청 함께하기 메서드")
    public void lectureRegisteredWithFriends(){

    }

}

