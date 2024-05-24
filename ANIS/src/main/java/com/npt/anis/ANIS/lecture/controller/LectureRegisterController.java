package com.npt.anis.ANIS.lecture.controller;

import com.npt.anis.ANIS.jwt.util.JwtUtil;
import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.service.LecturePresetService;
import com.npt.anis.ANIS.lecture.service.LectureRegisteredService;
import com.npt.anis.ANIS.lecture.service.LectureService;
import com.npt.anis.ANIS.member.domain.dto.MemberCreateDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registrations")
public class LectureRegisterController {
    @Qualifier("lecturePresetServiceImpl")
    private final LecturePresetService lecturePresetService;
    @Qualifier("lectureServiceImpl")
    private final LectureService lectureService;
    @Qualifier("lectureRegisteredServiceImpl")
    private final LectureRegisteredService lectureRegisteredService;
    private final JwtUtil jwtUtil;
    // 수강신청하기
    @PostMapping("")
    public ResponseEntity<Boolean> enrolment(@RequestBody List<LectureDto> lectureDtoList){
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean flag = lectureRegisteredService.lectureRegistered(userID,lectureDtoList);
        return ResponseEntity.ok(flag);
    }

    // 수강신청 친구들과 함께하기
    @PostMapping("/friends")
    public ResponseEntity<Boolean> enrolmentWithFriends(@RequestBody List<MemberSearchDTO> memberDtoList){
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean flag = lectureRegisteredService.lectureRegisteredWithFriends(memberDtoList,userID);
        return ResponseEntity.ok(flag);
    }
    // 친구 수강신청 따라하기
    @PostMapping("/{friendID}")
    public ResponseEntity<Boolean> enrolmentWithFriend(@PathVariable("friendID") String friendID){
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean flag = lectureRegisteredService.lectureRegisteredWithFriend(userID,friendID);
        return ResponseEntity.ok(flag);
    }
}
