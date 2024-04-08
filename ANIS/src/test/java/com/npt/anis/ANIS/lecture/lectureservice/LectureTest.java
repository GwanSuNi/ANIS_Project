package com.npt.anis.ANIS.lecture.lectureservice;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LecturePresetMapper;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import com.npt.anis.ANIS.lecture.service.LecturePresetService;
import com.npt.anis.ANIS.lecture.service.LecturePresetServiceImpl;
import com.npt.anis.ANIS.lecture.service.LectureService;
import com.npt.anis.ANIS.lecture.service.LectureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(MockitoExtension.class) // Mockito를 사용하여 Mock 객체를 주입할 경우
@SpringBootTest
@Rollback // 해당 테스트 메소드의 실행이 끝난 후 롤백을 수행함
public class LectureTest {
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private LecturePresetRepository lecturePresetRepository;
    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private LecturePresetMapper lecturePresetMapper;
    private LecturePresetService lecturePresetService;
    private LectureService lectureService;
    private LectureDto lectureDto;

    @BeforeEach
    public void dependencySetting(){
        lectureService = new LectureServiceImpl(lectureRepository, lectureMapper);
        lecturePresetService = new LecturePresetServiceImpl(lecturePresetRepository, lecturePresetMapper,lectureRepository);
        lectureDto = new LectureDto(1,1,"name",100,2024,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
    }

    /***
     * Lecture 에 GeneratedValue(Auto_increment) 옵션으로 인해서 lecID(기본키)값이 계속 증가해
     * dto를 통해서 값을 만들어준 후에 Lecture를 모두찾아 첫번째 값의 lecID와 비교해서 제대로 만들어졌는지 테스트
     */
    @Test
    @DisplayName("강의 생성하기 테스트")
    public void createTest(){
        lectureDto = new LectureDto(1,1,"name",100,2024,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
        Lecture lecture = lectureService.createLecture(lectureDto);
        assertEquals(lectureRepository.findAll().get(0).getLecID(), lecture.getLecID());
    }
    /***
     * 값을 업데이트 하기위해서 lecture 를 하나 새롭게 생성해준후 새로 생성된 lecture 를 바꾸기위해
     * dto 를 새로 만들어 주고 updateLecture 메서드 테스트
     */
    @Test
    @DisplayName("강의 수정하기 테스트")
    @Transactional
    public void lectureUpdateTest(){
        lectureDto = new LectureDto(1,1,"name",100,2024,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
        Lecture lecture = lectureService.createLecture(lectureDto);
        LectureDto newLecture = new LectureDto(1,1,"sanghyeon",300,2026,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
        lectureService.updateLecture(lecture.getLecID(),newLecture);
        assertEquals("sanghyeon",lecture.getLecName());
        assertEquals(300,lecture.getLecCredit());
        assertEquals(2026,lecture.getLecYear());
    }
    @Test
    @DisplayName("강의 삭제하기 테스트")
    public void lectureDeleteTest(){
        lectureDto = new LectureDto(1,1,"name",100,2024,1,"서상현","금요일", LocalDateTime.now(),LocalDateTime.now());
        Lecture lecture = lectureService.createLecture(lectureDto);
        assertEquals(true,lectureService.deleteLecture(lecture.getLecID()));
    }
}
