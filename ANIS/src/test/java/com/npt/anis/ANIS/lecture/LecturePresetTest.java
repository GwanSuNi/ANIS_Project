package com.npt.anis.ANIS.lecture;

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
        lectureService = new LectureServiceImpl(lectureRepository, lectureMapper,lecturePresetRepository);
        lecturePresetService = new LecturePresetServiceImpl(lecturePresetRepository,lecturePresetMapper,lectureRepository);
        lectureDto1 = new LectureDto(1L,1L,"name",100,2024,1,"서상현",5,1, LocalDateTime.now(),LocalDateTime.now());
        lectureDto2 = new LectureDto(2L,1L,"name",100,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
        lectureDto3 = new LectureDto(3L,1L,"name",100,2024,1,"서상현",5, 1,LocalDateTime.now(),LocalDateTime.now());
    }
}
