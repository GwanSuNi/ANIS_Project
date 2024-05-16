package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("lecturePresetServiceImpl")
public class LecturePresetServiceImpl implements LecturePresetService {
    private final LecturePresetRepository lecturePresetRepository;
    private final LecturePresetMapper lecturePresetMapper;
    private final LectureRepository lectureRepository;

    /***
     * @param lecturePresetDto 생성할 lecturePresetDto
     * @return lecturePresetDto 생성
     */
    public LecturePresetDto createLecturePreset(LecturePresetDto lecturePresetDto){
        LecturePreset lecturePreset = lecturePresetMapper.toEntity(lecturePresetDto);
        return lecturePresetMapper.toDto(lecturePresetRepository.save(lecturePreset));
    }

    /***
     * @return 현재년도와 학기에 맞는 모든 lecturePreset을 갖고옴
     */
    public List<LecturePresetDto> availableLecturePreset(){
        List<LecturePresetDto> lecturePresetList = lecturePresetMapper.toDtos(lecturePresetRepository.findAll());
        List<LecturePresetDto> availableLecturePresetList = new ArrayList<>();
        for (LecturePresetDto lecturePresetDto: lecturePresetList) {
            if(isCurrentSemester(lecturePresetDto.getLecSemester(),lecturePresetDto.getLecYear())){
                availableLecturePresetList.add(lecturePresetDto);
            }
        }
        return availableLecturePresetList;
    }
    // TODO LectureServiceImpl 과의 결합도를 굳이 높이고싶지않아서 같은 내용으로 재선언하였음
    /***
     * 현재 몇학기인지 반환하는 메서드
     * @return 1학기면 1 2학기면 2 반환
     */
    private int getCurrentSemester() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        //
        if (month >= 3 && month <= 7) {
            return 1;
        } else {
            return 2;
        }
    }

    /***
     * 현재년도 갖고오는 메서드
     * @return 현재년도로 정수값 반환
     */
    private int getCurrentYear() {
        LocalDate now = LocalDate.now();
        return now.getYear();
    }

    /***
     * 현재년도와 학기인지 아닌지 검증하는
     * @param semester 학기
     * @param year 년도
     * @return 현재년도와 학기가 맞으면 true 아니면 false 반환
     */
    private boolean isCurrentSemester(int semester, int year) {
        int currentSemester = getCurrentSemester();
        int currentYear = getCurrentYear();
        return semester == currentSemester && year == currentYear;
    }
}
