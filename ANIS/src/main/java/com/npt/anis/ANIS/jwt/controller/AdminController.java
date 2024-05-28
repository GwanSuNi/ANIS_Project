package com.npt.anis.ANIS.jwt.controller;

import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
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

}

