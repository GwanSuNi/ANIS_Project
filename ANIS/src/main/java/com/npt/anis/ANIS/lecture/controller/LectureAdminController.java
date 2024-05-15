package com.npt.anis.ANIS.lecture.controller;

import com.npt.anis.ANIS.global.exception.ValidationException;
import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.service.LecturePresetService;
import com.npt.anis.ANIS.lecture.service.LectureRegisteredService;
import com.npt.anis.ANIS.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectureAdmin")
public class LectureAdminController {
//            jwt 아이디찾는방법
//            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    @Qualifier("lecturePresetServiceImpl")
    private final LecturePresetService lecturePresetService;
    @Qualifier("lectureServiceImpl")
    private final LectureService lectureService;
    @Qualifier("lectureRegisteredServiceImpl")
    private final LectureRegisteredService lectureRegisteredService;
    // TODO 값 검증해야할게 더 있다면 추가해야함
    /***
     * 어드민에서 강의를 생성하는 컨트롤러
     * 강의 시간이 겹치는지 안겹치는지에 대한 검증만 해놓음
     * @param newLectureDto 생성할 강의를 매개변수로 넘겨줌
     * @return
     */
    @PostMapping("/lecture")
    private ResponseEntity<Boolean> createLecture(@RequestBody LectureDto newLectureDto) {
        List<LectureDto> lectureDtoList = lectureService.showAvailableLectureList();
        for (LectureDto lectureDto : lectureDtoList) {
            if (lectureDto.getLecDay() == newLectureDto.getLecDay() &&
                    lectureService.areTimesOverlap(newLectureDto, lectureDto)) {
                // 에러 내보내기
                throw new ValidationException("강의 시간이 겹칩니다. 다시 확인해주세요");
            }
        }
        lectureService.createLecture(newLectureDto);
        return ResponseEntity.ok(true);
    }

    /**
     * 현재 등록한 강의들을 넘겨주는 컨트롤러 (어드민이 등록되어있는 강의들을 봐가면서 해야하기때문에 만듬)
     * @return
     */
    @GetMapping("/lecture")
    private ResponseEntity<List<LectureDto>> showLectureList(){
        List<LectureDto> lectureDtoList = lectureService.showAvailableLectureList();
        return ResponseEntity.ok(lectureDtoList);
    }
}
