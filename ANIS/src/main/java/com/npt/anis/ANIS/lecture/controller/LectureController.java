package com.npt.anis.ANIS.lecture.controller;
import com.npt.anis.ANIS.lecture.service.LectureRegisteredService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.service.LecturePresetService;
import com.npt.anis.ANIS.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecture")
public class LectureController {
//            jwt 아이디찾는방법
//            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    @Qualifier("lecturePresetServiceImpl")
    private final LecturePresetService lecturePresetService;
    @Qualifier("lectureServiceImpl")
    private final LectureService lectureService;
    @Qualifier("lectureRegisteredServiceImpl")
    private final LectureRegisteredService lectureRegisteredService;

    // 수강신청 페이지 상단의 현재 년도와 학기에 따라서 프리셋 리스트를 전달해주는 api
    @GetMapping("/lecturePreset")
    public ResponseEntity<List<LecturePresetDto>> lecturePresetList(){
       List<LecturePresetDto> lecturePresetDtoList = lecturePresetService.availableLecturePreset();
       return ResponseEntity.ok(lecturePresetDtoList);
    }

    // lecturePreset 의 등록되어있는 강의 리스트 갖고오기
    @GetMapping("/lecturePreset/{lecturePresetName}")
    public ResponseEntity<List<LectureDto>> lecturePresetOfLectureList(@PathVariable("lecturePresetName") String lecturePresetName){
        long lpIndex = lecturePresetService.getLecturePresetIndex(lecturePresetName);
        List<LectureDto> lectureDtoList = lectureService.getLectureListByPreset(lpIndex);
        return ResponseEntity.ok(lectureDtoList);
    }
    // 현재 수강 가능한 강의 리스트 갖고오기
    @GetMapping("/availableLectureList")
    public ResponseEntity<List<LectureDto>> userAvailableLectureList(){
        List<LectureDto> lectureDtoList = lectureService.showAvailableLectureList();
        return ResponseEntity.ok(lectureDtoList);
    }
    // 현재 로그인 되어있는 회원의 강의 리스트 갖고오기
    @GetMapping("/lectureList")
    public ResponseEntity<List<LectureDto>> userLectureList(){
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        List<LectureDto> lectureDtoList = lectureRegisteredService.studentLectureList(userID);
        return ResponseEntity.ok(lectureDtoList);
    }
    // 내가 알고싶은 친구의 아이디로 강의 리스트 갖고오기
    @GetMapping("/lectureList/{friendID}")
    public ResponseEntity<List<LectureDto>> friendLectureList(@PathVariable("friendID")String friendID){
        List<LectureDto> lectureDtoList = lectureRegisteredService.studentLectureList(friendID);
        return ResponseEntity.ok(lectureDtoList);
    }
}
