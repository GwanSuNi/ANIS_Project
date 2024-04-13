package com.npt.anis.ANIS.jwt.controller;

import com.npt.anis.ANIS.jwt.dto.JoinDTO;
import com.npt.anis.ANIS.jwt.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@Value("${spring.password}") String password, JoinDTO joinDto) { // 설정파일에 있는 고정된 비밀번호 사용
        joinService.joinProcess(password, joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
