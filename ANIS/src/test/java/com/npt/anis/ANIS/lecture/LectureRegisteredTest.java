package com.npt.anis.ANIS.lecture;
import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.entity.LectureRegistered;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LecturePresetMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureRegisteredMapper;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRegisteredRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import com.npt.anis.ANIS.lecture.service.*;
import com.npt.anis.ANIS.member.domain.dto.MemberDto;
import com.npt.anis.ANIS.member.domain.entity.Member;
import com.npt.anis.ANIS.member.repository.MemberRepository;
import jakarta.annotation.security.RolesAllowed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private LectureRegisteredMapper lectureRegisteredMapper;
    @Autowired
    private MemberRepository memberRepository;


    // TODO 수강신청 로직이 바뀌어야하기때문에 잠시 보류하고
    //  수강신청 로직이 바뀐 후 완성된 테스트코드를 작성하겠습니다
    @BeforeEach
    public void dependencySetting(){
        // DDL 로 만든 더미데이터를 공유함
    }
    @Test
    @DisplayName("수강신청테스트")
    @Transactional
    public void lectureRegistered(){
        // given
        List<LectureDto> lectureDtoList = lectureService.getLectureListByPreset(3L);
        // when
        boolean flag = lectureRegisteredService.lectureRegistered("19831033",lectureDtoList);
        List<LectureDto> lectureDtos = lectureRegisteredService.studentLectureList("19831033");
        // then
        assertEquals(8,lectureDtoList.size());
        assertEquals(true,flag);
        assertEquals(8,lectureDtos.size());
    }
    @Test
    @DisplayName("수강신청 수정하기 테스트")
    @Transactional
    public void lectureRegisteredUpdate(){
        // given
        List<LectureDto> lectureDtoUpdateList = lectureService.getLectureListByPreset(2L);
        // when
        boolean flag = lectureRegisteredService.lectureRegistered("19831033",lectureDtoUpdateList);
        List<LectureDto> lectureDtos = lectureRegisteredService.studentLectureList("19831033");
        // then
        assertEquals(9,lectureDtoUpdateList.size());
        assertEquals(true,flag);
        assertEquals(9,lectureDtos.size());
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
        // when
        boolean flag = lectureRegisteredService.lectureRegistered("상현",lectureDtoList);
        // then
        assertEquals(false,flag);
    }

    @Test
    @DisplayName("친구 수강신청 받아오기 테스트")
    public void friendOfLectureTest(){
        // given
        // 데이터가 이미 있음
        // when
        List<LectureDto> lectureDtos = lectureRegisteredService.studentLectureList("19831033");
        // then
        assertEquals(9,lectureDtos.size());
    }
    @Test
    @DisplayName("친구 수강신청 따라하기 메서드")
    @Transactional
    public void lectureRegisteredWithFriend(){
        // given
        // 데이터 이미 있음
        // when
        lectureRegisteredService.lectureRegisteredWithFriend("20244013","19831033");
        List<LectureDto> lectureDtos = lectureRegisteredService.studentLectureList("20244013");
        // then
        assertEquals(9,lectureDtos.size());
    }
    @Test
    @DisplayName("해당 학생이 수강신청 했는지 확인하는 메서드")
    @Transactional
    public void havaLectureRegistered(){
        boolean flag = lectureRegisteredRepository.existsByStudentID("19831033");
        assertEquals(true,flag);
    }
}

