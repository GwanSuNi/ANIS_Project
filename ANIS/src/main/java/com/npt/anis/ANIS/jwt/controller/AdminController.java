package com.npt.anis.ANIS.jwt.controller;

import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
@RequestMapping("/api/admin")
public class AdminController {
    private final MemberService memberService;

    @GetMapping("/")
    public String admin() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Admin Controller: " + name;
    }

    @GetMapping("/member/{studentID}")
    public ResponseEntity<MemberDTO> memberSearch(@PathVariable("studentID") String studentID) {
        MemberDTO memberDTO = memberService.getMemberDetail(studentID);
        if (memberDTO == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(memberDTO);
    }

    // 학번을 배열로 받으면 탈퇴 여부를 반전시키는 컨트롤러
    @PutMapping("/member/quit")
    public ResponseEntity<Integer> toggleMemberQuit(@RequestBody String[] memberIDs) {
        int result = memberService.toggleMemberQuit(memberIDs);
        if (result > 0) {
            return ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(0);
    }
}

