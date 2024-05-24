package com.npt.anis.ANIS.jwt.controller;

import com.npt.anis.ANIS.jwt.dto.JoinDTO;
import com.npt.anis.ANIS.jwt.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    // TODO: 가입 실패시 처리
    @PostMapping("/join")
    public ResponseEntity<String> join(@Value("${spring.password}") String password, @RequestBody JoinDTO joinDto) { // 설정파일에 있는 고정된 비밀번호 사용
        joinService.joinProcess(password, joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: SignUp 페이지 분류에 따라 이 API로 Axios 요청하게 훅스 생성
    @PostMapping("/join/admin")
    public ResponseEntity<String> joinAdmin(@RequestBody JoinDTO joinDto) { // 관리자 유저는 개인의 비밀번호를 사용
        joinService.joinProcess(joinDto.getPassword(), joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/joinList")
    public ResponseEntity<String> join(@Value("${spring.password}") String password, @RequestBody List<JoinDTO> joinDTOList) {
        joinService.joinProcess(password, joinDTOList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
