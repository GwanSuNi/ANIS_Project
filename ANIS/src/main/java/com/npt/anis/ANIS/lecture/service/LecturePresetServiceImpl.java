package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.global.util.DateUtils;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import com.npt.anis.ANIS.lecture.domain.mapper.LecturePresetMapper;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            if(DateUtils.isCurrentSemester(lecturePresetDto.getLecSemester(),lecturePresetDto.getLecYear())){
                availableLecturePresetList.add(lecturePresetDto);
            }
        }
        return availableLecturePresetList;
    }

    /***
     * LecturePresetName 을 입력받으면 해당 학기와 년도의 lpIndex 를 반환받음
     * @param lpName LecturePresetName
     * @return 해당 하기와 년도의 lpIndex
     */
    @Override
    public long getLecturePresetIndex(String lpName){
        List<LecturePresetDto> lecturePresetList = lecturePresetMapper.toDtos(lecturePresetRepository.findByPresetName(lpName));
        long lpIndex = 0;
        int currentYear = DateUtils.getCurrentYear();
        int currentSemester = DateUtils.getCurrentSemester();

        for (LecturePresetDto lecturePresetDto : lecturePresetList) {
            if (DateUtils.isCurrentSemester(lecturePresetDto.getLecSemester(), lecturePresetDto.getLecYear())) {
                lpIndex = lecturePresetDto.getLpIndex();
                break;
            }
        }
        return lpIndex;
    }
}
