package com.npt.anis.ANIS.lecture;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
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

import java.time.LocalTime;
import java.time.LocalTime;
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
    private LecturePresetDto lecturePresetDto;


    @BeforeEach
    public void dependencySetting(){
        lectureService = new LectureServiceImpl(lectureRepository, lectureMapper,lecturePresetRepository);
        lecturePresetService = new LecturePresetServiceImpl(lecturePresetRepository, lecturePresetMapper,lectureRepository);
        lectureDto = new LectureDto(1L,1L,"name",100,2024,1,"서상현","금요일",1, LocalTime.now(),LocalTime.now(),"효행관 401호");
    }

    /***
     * Lecture 에 GeneratedValue(Auto_increment) 옵션으로 인해서 lecID(기본키)값이 계속 증가해
     * dto를 통해서 값을 만들어준 후에 Lecture를 모두찾아 첫번째 값의 lecID와 비교해서 제대로 만들어졌는지 테스트
     */
    @Test
    @DisplayName("강의 생성하기 테스트")
    public void createTest(){
        // given 테스트를 위해 준비하는 과정 테스트에 사용하는 변수, 입력 값 등을 정의하거나 Mock 객체를 정의하는 구문도 포함된다
        lectureDto = new LectureDto(1L,1L,"name",100,2024,1,"서상현","금요일",1, LocalTime.now(),LocalTime.now(),"효행관 401호");
        // when 실제로 액션을 하는 테스트를 실행하는 과정
        LectureDto lecturedto = lectureService.createLecture(lectureDto);
        // then 테스트를 검증하는 과정
        assertEquals(true,lectureRepository.existsById(lecturedto.getLecID()));
    }
    /***
     * 값을 업데이트 하기위해서 lecture 를 하나 새롭게 생성해준후 새로 생성된 lecture 를 바꾸기위해
     * dto 를 새로 만들어 주고 updateLecture 메서드 테스트
     */
    @Test
    @DisplayName("강의 수정하기 테스트")
    public void lectureUpdateTest(){
        // given
        lectureDto = new LectureDto(1L,1L,"name",100,2024,1,"서상현","금요일",1, LocalTime.now(),LocalTime.now(),"효행관 401호");
        LectureDto lecturedto = lectureService.createLecture(lectureDto);
        LectureDto newLecture = new LectureDto(1L,1L,"sanghyeon",300,2026,1,"서상현","금요일",1, LocalTime.now(),LocalTime.now(),"효행관 401호");
        // when
        LectureDto updateLectureDto = lectureService.updateLecture(newLecture.getLecID(),newLecture);
        // then
        assertEquals("sanghyeon",updateLectureDto.getLecName());
        assertEquals(300,updateLectureDto.getLecCredit());
        assertEquals(2026,updateLectureDto.getLecYear());
    }
    @Test
    @DisplayName("강의 삭제하기 테스트")
    public void lectureDeleteTest(){
        // given
        LectureDto lecturedto = lectureService.createLecture(lectureDto);
        // when
        // 해당 메서드가 삭제가 잘 되었다면 true, 삭제하지못했다면 false
        boolean flag = lectureService.deleteLecture(lecturedto.getLecID());
        // then
        assertEquals(true,flag);
    }

    // 지금부터는 더미데이터를 받아왔다고 가정하고 만들었습니다 더미데이터는 resources/dummydata/insert_dummy_data.sql 에 있습니다
    @Test
    @DisplayName("프리셋에 등록할 수 있는 강의 목록 갖고오기 lpIndex == null 인 애들 갖고오기")
    public void showNoPresetLectureList(){
        // given

        // when
        List<LectureDto> lectureDtoList = lectureService.showNoPresetLectureList();
        // then
        assertEquals(10,lectureDtoList.size());
    }
    @Test
    @DisplayName("프리셋에 대한 강의목록 갖고오기")
    public void getLecturePresetByLecture(){
        // given
        // when
        List<LectureDto> lectureList = lectureService.getLectureListByPreset(2);
        // then
        assertEquals(8,lectureList.size());
    }
    @Test
    @DisplayName("프리셋에 대한 강의목록 수정하기")
    public void updatePresetOfLectureList(){
        // given
        List<LectureDto> newLectureList = new ArrayList<>();
        lectureDto = new LectureDto(1L,null,"name",100,2024,1,"서상현","금요일",1, LocalTime.now(),LocalTime.now(),"효행관 401호");
        lectureRepository.save(lectureMapper.toEntity(lectureDto));
        newLectureList.add(lectureDto);
        // when
        List<LectureDto> lectureDtoList = lectureService.getLectureListByPreset(2L);
        List<LectureDto> realNewLectureList = lectureService.updateLecturePresetOfLectureList(2L, newLectureList);
        // then
        assertEquals(1,realNewLectureList.size());
        assertEquals(8,lectureDtoList.size());
    }
    @Test
    @DisplayName("강의 현재년도,학기 갖고오는지 테스트")
    public void lectureYearTest(){
        // given
        // when
        List<LectureDto> lectureList = lectureService.showAvailableLectureList();
        // then
        assertEquals(10,lectureList.size());
    }
}
