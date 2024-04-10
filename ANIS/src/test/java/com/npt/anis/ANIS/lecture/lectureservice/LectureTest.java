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
import jakarta.persistence.Tuple;
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
import java.util.ArrayList;
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
    private LectureDto lectureDto1;
    private LectureDto lectureDto2;
    private LectureDto lectureDto3;
    private LectureDto updateLectureDto1;
    private LectureDto updateLectureDto2;
    private LectureDto updateLectureDto3;
    private LectureDto updateLectureDto4;


    @BeforeEach
    public void dependencySetting(){
        lectureService = new LectureServiceImpl(lectureRepository, lectureMapper,lecturePresetRepository);
        lecturePresetService = new LecturePresetServiceImpl(lecturePresetRepository, lecturePresetMapper,lectureRepository);
        lectureDto = new LectureDto(1,1,"name",100,2024,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        // 현재년도가 아닌 lecture
        lectureDto1 = new LectureDto(2,1,"name",200,2025,2,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        lectureDto2 = new LectureDto(3,1,"name",300,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        lectureDto3 = new LectureDto(4,1,"name",400,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        updateLectureDto1 = new LectureDto(5,2,"a",400,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        updateLectureDto2 = new LectureDto(6,2,"b",400,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        updateLectureDto3 = new LectureDto(7,2,"c",400,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        updateLectureDto4 = new LectureDto(8,2,"d",400,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
    }

    /***
     * Lecture 에 GeneratedValue(Auto_increment) 옵션으로 인해서 lecID(기본키)값이 계속 증가해
     * dto를 통해서 값을 만들어준 후에 Lecture를 모두찾아 첫번째 값의 lecID와 비교해서 제대로 만들어졌는지 테스트
     */
    @Test
    @DisplayName("강의 생성하기 테스트")
    public void createTest(){
        // given 테스트를 위해 준비하는 과정 테스트에 사용하는 변수, 입력 값 등을 정의하거나 Mock 객체를 정의하는 구문도 포함된다
        Lecture lecture = lectureService.createLecture(lectureDto);
        // when 실제로 액션을 하는 테스트를 실행하는 과정

        // then 테스트를 검증하는 과정
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
        // given
        Lecture lecture = lectureService.createLecture(lectureDto);
        LectureDto newLecture = new LectureDto(1,1,"sanghyeon",300,2026,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        // when
        lectureService.updateLecture(lecture.getLecID(),newLecture);
        // then
        assertEquals("sanghyeon",lecture.getLecName());
        assertEquals(300,lecture.getLecCredit());
        assertEquals(2026,lecture.getLecYear());
    }
    @Test
    @DisplayName("강의 삭제하기 테스트")
    public void lectureDeleteTest(){
        // given
        Lecture lecture = lectureService.createLecture(lectureDto);
        // when
        // 해당 메서드가 삭제가 잘 되었다면 true, 삭제하지못했다면 false
        boolean flag = lectureService.deleteLecture(lecture.getLecID());
        // then lecture를 모두 갖고와서 길이를 재었을떄 0이면 잘 지워짐
        assertEquals(true,flag);
        assertEquals(0,lectureRepository.findAll().size());
    }
    @Test
    @DisplayName("프리셋에 대한 강의목록 갖고오기")
    public void getLecturePresetByLecture(){
        // given
        LecturePreset lecturePreset = new LecturePreset();
        lecturePreset.setPresetName("A");
        lecturePresetRepository.save(lecturePreset);
        lectureDto = new LectureDto(1,lecturePreset.getLpIndex(),"name",100,2024,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        lectureDto1 = new LectureDto(2,lecturePreset.getLpIndex(),"name",200,2024,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        lectureDto2 = new LectureDto(3,lecturePreset.getLpIndex(),"name",300,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        lectureDto3 = new LectureDto(4,lecturePreset.getLpIndex(),"name",400,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        Lecture lecture = lectureService.createLecture(lectureDto);
        Lecture lecture1 = lectureService.createLecture(lectureDto1);
        Lecture lecture2 = lectureService.createLecture(lectureDto2);
        Lecture lecture3 = lectureService.createLecture(lectureDto3);
        // when
        List<Lecture> lectureList = lectureService.getLectureListByPreset(lecturePreset.getLpIndex());
        // then
        assertEquals(100,lectureList.get(0).getLecCredit());
        assertEquals(200,lectureList.get(1).getLecCredit());
        assertEquals(300,lectureList.get(2).getLecCredit());
        assertEquals(400,lectureList.get(3).getLecCredit());
        assertEquals(4,lectureList.size());
    }
    @Test
    @DisplayName("프리셋에 대한 강의목록 수정하기")
    public void updatePresetOfLectureList(){
        // given
        LecturePreset lecturePreset = new LecturePreset();
        lecturePreset.setPresetName("A");
        // lecturePreset생성
        lecturePresetRepository.save(lecturePreset);
        lectureDto = new LectureDto(1,lecturePreset.getLpIndex(),"name",100,2024,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        lectureDto1 = new LectureDto(2,lecturePreset.getLpIndex(),"name",200,2024,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        lectureDto2 = new LectureDto(3,lecturePreset.getLpIndex(),"name",300,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        lectureDto3 = new LectureDto(4,lecturePreset.getLpIndex(),"name",400,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        // preset에 대한 lecture목록 생성
        Lecture lecture = lectureService.createLecture(lectureDto);
        Lecture lecture1 = lectureService.createLecture(lectureDto1);
        Lecture lecture2 = lectureService.createLecture(lectureDto2);
        Lecture lecture3 = lectureService.createLecture(lectureDto3);
        // update 할 lecture 목록 생성
        Lecture lecture4 = lectureService.createLecture(updateLectureDto1);
        Lecture lecture5 = lectureService.createLecture(updateLectureDto2);
        Lecture lecture6 = lectureService.createLecture(updateLectureDto3);
        Lecture lecture7 = lectureService.createLecture(updateLectureDto4);
        // lectureList저장
        List<Lecture> newLectureList = new ArrayList<>();
        newLectureList.add(lecture4);
        newLectureList.add(lecture5);
        newLectureList.add(lecture6);
        newLectureList.add(lecture7);
        // when
        // preset 에 대한 lecture List 생
        List<Lecture> lectureList = lectureService.getLectureListByPreset(lecturePreset.getLpIndex());
        List<Lecture> realNewLectureList = lectureService.updateLecturePresetOfLectureList(lecturePreset.getLpIndex(), newLectureList);
        // then
        assertEquals(4,realNewLectureList.size());
        assertEquals("a",realNewLectureList.get(0).getLecName());
        assertEquals("b",realNewLectureList.get(1).getLecName());
        assertEquals("c",realNewLectureList.get(2).getLecName());
        assertEquals("d",realNewLectureList.get(3).getLecName());
    }
    @Test
    @DisplayName("강의 현재년도,학기 갖고오는지 테스트")
    public void lectureYearTest(){
        // given
        // 현재년도와 학기인 강의 -> 2024 , 1
        Lecture lecture = lectureService.createLecture(lectureDto);
        // 현재년도와 학기가 아닌 강의 -> 2025 , 2
        Lecture lecture1 = lectureService.createLecture(lectureDto1);
        // when
        List<Lecture> lectureList = lectureService.showAvailableLectureList();
        // then
        assertEquals(1,lectureList.size());
        assertEquals(2024,lectureList.get(0).getLecYear());
    }
}
